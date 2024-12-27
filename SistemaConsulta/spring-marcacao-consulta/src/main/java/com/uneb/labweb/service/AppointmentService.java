package com.uneb.labweb.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.mapper.AppointmentMapper;
import com.uneb.labweb.dto.mapper.DoctorMapper;
import com.uneb.labweb.dto.request.AppointmentDTO;
import com.uneb.labweb.dto.response.AppointmentResponseDTO;
import com.uneb.labweb.dto.response.AppointmentsByDateDTO;
import com.uneb.labweb.dto.response.DoctorResponseDTO;
import com.uneb.labweb.dto.response.MonthlyAppointmentStatsDTO;
import com.uneb.labweb.dto.response.YearsWithAppointmentsDTO;
import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.enums.UserStatus;
import com.uneb.labweb.exception.AppointmentCancelException;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.model.Doctor;
import com.uneb.labweb.model.HealthCenter;
import com.uneb.labweb.model.Penalty;
import com.uneb.labweb.model.Specialty;
import com.uneb.labweb.model.User;
import com.uneb.labweb.repository.AppointmentRepository;
import com.uneb.labweb.repository.HealthCenterRepository;
import com.uneb.labweb.repository.PenaltyRepository;
import com.uneb.labweb.repository.SpecialtyRepository;
import com.uneb.labweb.repository.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class AppointmentService {

    // Formato de data para exibição
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Repositórios e mapeadores necessários para operações de consulta e transformação de dados
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final HealthCenterRepository healthCenterRepository;
    private final SpecialtyRepository specialtyRepository;
    private final PenaltyRepository penaltyRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorMapper doctorMapper;

    // Construtor para inicializar os repositórios e mapeadores
    public AppointmentService(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            HealthCenterRepository healthCenterRepository,
            SpecialtyRepository specialtyRepository,
            PenaltyRepository penaltyRepository,
            AppointmentMapper appointmentMapper,
            DoctorMapper doctorMapper
    ) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.healthCenterRepository = healthCenterRepository;
        this.specialtyRepository = specialtyRepository;
        this.penaltyRepository = penaltyRepository;
        this.appointmentMapper = appointmentMapper;
        this.doctorMapper = doctorMapper;
    }

    // Retorna todos os agendamentos existentes
    public List<AppointmentResponseDTO> findAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDTO)
                .toList();
    }

    // Retorna um grupo de agendamentos por data, para uma especialidade e posto de saúde específicos
    public List<AppointmentsByDateDTO> findAppointmentsGroup(
            @NotNull @Positive Long healthCenterId,
            @NotNull @Positive Long specialtyId
    ) {
        // Verifica a existência da especialidade e posto de saúde
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RecordNotFoundException("Especialidade não encontrada com o id: " + specialtyId));

        HealthCenter healthCenter = healthCenterRepository.findById(healthCenterId)
                .orElseThrow(() -> new RecordNotFoundException("Posto de saúde não encontrado com o id: " + healthCenterId));

        // Recupera os agendamentos agrupados por data
        return fetchAppointmentsGroup(specialty, healthCenter);
    }

    // Função auxiliar que busca agendamentos agrupados por data
    private List<AppointmentsByDateDTO> fetchAppointmentsGroup(Specialty specialty, HealthCenter healthCenter) {
        List<AppointmentsByDateDTO> AppointmentsByDateDTOList = new ArrayList<>();

        // Recupera todas as datas distintas de agendamento
        List<LocalDate> dates = appointmentRepository.findDistinctDates(specialty.getId(), healthCenter.getId())
                .stream()
                .map(java.sql.Date::toLocalDate)
                .toList();

        // Para cada data, busca os médicos e seus respectivos agendamentos
        for (LocalDate date : dates) {
            List<DoctorResponseDTO> doctorDTOList = new ArrayList<>();
            List<Doctor> doctors = appointmentRepository.findDoctorsByDate(date, specialty.getId(), healthCenter.getId());

            // Para cada médico, verifica os agendamentos pendentes
            for (Doctor doctor : doctors) {
                List<AppointmentResponseDTO> appointmentDTOList = appointmentRepository.findByDateAndDoctor(
                        date, doctor.getId(), specialty.getId(), healthCenter.getId())
                        .stream()
                        .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.PENDING && appointment.getUser() == null)
                        .map(appointmentMapper::toDTO)
                        .toList();

                // Se houver agendamentos, mapeia o médico com os agendamentos
                if (!appointmentDTOList.isEmpty()) {
                    DoctorResponseDTO doctorDTO = doctorMapper.toDTOwithAppointments(doctor, appointmentDTOList);
                    doctorDTOList.add(doctorDTO);
                }
            }

            // Se houver médicos com agendamentos, adiciona à lista de agendamentos por data
            if (!doctorDTOList.isEmpty()) {
                String dateString = date.format(dateFormatter);
                AppointmentsByDateDTO appointmentsByDateDTO = new AppointmentsByDateDTO(dateString, doctorDTOList);
                AppointmentsByDateDTOList.add(appointmentsByDateDTO);
            }
        }

        return AppointmentsByDateDTOList;
    }

    // Retorna os anos com agendamentos, incluindo as estatísticas mensais de agendamentos
    public List<YearsWithAppointmentsDTO> getYearsWithAppointments() {
        List<YearsWithAppointmentsDTO> yearsWithAppointmentsDTOList = new ArrayList<>();
        List<Integer> years = appointmentRepository.findDistinctYears();

        // Para cada ano, recupera as estatísticas mensais de agendamentos
        for (Integer year : years) {
            List<MonthlyAppointmentStatsDTO> monthlyStats = new ArrayList<>();

            // Recupera os resultados de agendamentos agendados, atendidos, perdidos e cancelados por mês
            List<Object[]> scheduledResults = appointmentRepository.countScheduledAppointmentsMonth(year);
            List<Object[]> attendedResults = appointmentRepository.countAppointmentsByStatusAndMonth(year, "attended");
            List<Object[]> missedResults = appointmentRepository.countAppointmentsByStatusAndMonth(year, "missed");
            List<Object[]> cancelledResults = appointmentRepository.countCancelledAppointmentsByMonth(year);

            // Para cada mês, encontra os contadores correspondentes
            for (int month = 1; month <= 12; month++) {
                int scheduledCount = 0;
                int attendedCount = 0;
                int missedCount = 0;
                int cancelledCount = 0;

                // Verifica os resultados de cada tipo de agendamento para o mês
                for (Object[] result : scheduledResults) {
                    if ((Integer) result[0] == month) {
                        scheduledCount = (Integer) result[1];
                        break;
                    }
                }

                for (Object[] result : attendedResults) {
                    if ((Integer) result[0] == month) {
                        attendedCount = (Integer) result[1];
                        break;
                    }
                }

                for (Object[] result : missedResults) {
                    if ((Integer) result[0] == month) {
                        missedCount = (Integer) result[1];
                        break;
                    }
                }

                for (Object[] result : cancelledResults) {
                    if ((Integer) result[0] == month) {
                        cancelledCount = (Integer) result[1];
                        break;
                    }
                }

                // Adiciona as estatísticas mensais ao DTO
                monthlyStats.add(new MonthlyAppointmentStatsDTO(month, scheduledCount, attendedCount, missedCount, cancelledCount));
            }

            // Adiciona as estatísticas anuais ao DTO final
            yearsWithAppointmentsDTOList.add(new YearsWithAppointmentsDTO(year, monthlyStats));
        }

        return yearsWithAppointmentsDTOList;
    }

    // Retorna todos os agendamentos de um usuário específico
    public List<AppointmentResponseDTO> findAppointmentsByUser(@NotNull @Positive Long userId) {
        return userRepository.findById(userId)
                .map(recordFound -> {
                    return appointmentRepository.findByUserId(userId)
                            .stream()
                            .map(appointmentMapper::toDTO)
                            .toList();
                })
                .orElseThrow(() -> new RecordNotFoundException("Usuário não encontrado com o id: " + userId));
    }

    // Retorna um agendamento específico pelo id
    public AppointmentResponseDTO findAppointmentById(@NotNull @Positive Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Cria um novo agendamento a partir de um DTO
    public AppointmentResponseDTO createAppointment(@Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentMapper.toDTO(appointmentRepository.save(appointmentMapper.toEntity(appointmentDTO)));
    }

    // Atualiza um agendamento existente com as informações do DTO
    public AppointmentResponseDTO updateAppointment(@NotNull @Positive Long id, @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setAppointmentDateTime(appointmentMapper.parseDateTime(appointmentDTO.appointmentDateTime()));
                    recordFound.setAppointmentStatus(appointmentMapper.convertAppointmentStatusValue(appointmentDTO.appointmentStatus()));

                    return appointmentMapper.toDTO(appointmentRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Agenda um agendamento, associando um usuário à consulta
    public AppointmentResponseDTO scheduleAppointment(@NotNull @Positive Long id, @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentRepository.findById(id)
                .map(recordFound -> {
                    Optional<User> user = userRepository.findById(appointmentDTO.patientId());

                    if (user.isEmpty()) {
                        throw new RecordNotFoundException("Usuário não encontrado com o id: " + appointmentDTO.patientId());
                    }

                    recordFound.setUser(user.get());
                    recordFound.setAppointmentStatus(AppointmentStatus.SCHEDULED);

                    return appointmentMapper.toDTO(appointmentRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Cancela um agendamento, se estiver no estado "SCHEDULED"
    public AppointmentResponseDTO cancelAppointment(@NotNull @Positive Long id, @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentRepository.findById(id)
                .map(recordFound -> {
                    // Verifica se o agendamento está no estado "SCHEDULED"
                    if (recordFound.getAppointmentStatus() == AppointmentStatus.SCHEDULED) {
                        // Remove a associação com o usuário e altera o status do agendamento para "PENDING"
                        recordFound.setUser(null);
                        recordFound.setAppointmentStatus(AppointmentStatus.PENDING);

                        // Incrementa o contador de cancelamentos
                        recordFound.setCancellationCount(recordFound.getCancellationCount() + 1);

                        // Obtém a data da consulta e a data atual
                        LocalDate appointmentDate = recordFound.getAppointmentDateTime().toLocalDate();
                        LocalDate today = LocalDate.now();

                        // Verifica se a consulta foi cancelada um dia antes de sua data agendada
                        if (appointmentDate.isEqual(today.plusDays(1))) {
                            // Busca o usuário associado ao ID do paciente
                            Optional<User> user = userRepository.findById(appointmentDTO.patientId());

                            // Lança exceção caso o usuário não seja encontrado
                            if (user.isEmpty()) {
                                throw new RecordNotFoundException("Usuário não encontrado com o id: " + appointmentDTO.patientId());
                            }

                            // Altera o status do usuário para "BLOCKED" devido ao cancelamento em curto prazo
                            user.get().setUserStatus(UserStatus.BLOCKED);
                            userRepository.save(user.get());

                            // Define as datas de início e término da penalidade
                            LocalDate penaltyStartDate = today.plusDays(1); // Penalidade começa amanhã
                            LocalDate penaltyEndDate = penaltyStartDate.plusWeeks(1); // Penalidade termina após uma semana

                            // Cria e salva a penalidade associada ao usuário
                            Penalty penalty = new Penalty();
                            penalty.setPenaltyStartDate(penaltyStartDate);
                            penalty.setPenaltyEndDate(penaltyEndDate);
                            penalty.setUser(user.get());
                            penaltyRepository.save(penalty);
                        }
                    } else {
                        // Lança exceção se o agendamento não puder ser cancelado devido ao estado atual
                        throw new AppointmentCancelException("Não é possível desmarcar uma consulta com o estado: " + recordFound.getAppointmentStatus());
                    }

                    // Salva o agendamento com as alterações realizadas e retorna o DTO correspondente
                    return appointmentMapper.toDTO(appointmentRepository.save(recordFound));
                })
                // Lança exceção caso o agendamento não seja encontrado
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Deleta um agendamento com base no id
    public void deleteAppointment(@NotNull @Positive Long id) {
        appointmentRepository.delete(appointmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
