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
import com.uneb.labweb.exception.AppointmentCancelException;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.model.Doctor;
import com.uneb.labweb.model.HealthCenter;
import com.uneb.labweb.model.Specialty;
import com.uneb.labweb.model.User;
import com.uneb.labweb.repository.AppointmentRepository;
import com.uneb.labweb.repository.HealthCenterRepository;
import com.uneb.labweb.repository.SpecialtyRepository;
import com.uneb.labweb.repository.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class AppointmentService {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final HealthCenterRepository healthCenterRepository;
    private final SpecialtyRepository specialtyRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorMapper doctorMapper;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            HealthCenterRepository healthCenterRepository,
            SpecialtyRepository specialtyRepository,
            AppointmentMapper appointmentMapper,
            DoctorMapper doctorMapper
    ) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.healthCenterRepository = healthCenterRepository;
        this.specialtyRepository = specialtyRepository;
        this.appointmentMapper = appointmentMapper;
        this.doctorMapper = doctorMapper;

    }

    public List<AppointmentResponseDTO> findAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDTO)
                .toList();
    }

    public List<AppointmentsByDateDTO> findAppointmentsGroup(
        @NotNull @Positive Long healthCenterId,
        @NotNull @Positive Long specialtyId
    ) {
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RecordNotFoundException("Especialidade não encontrada com o id: " + specialtyId));

        HealthCenter healthCenter = healthCenterRepository.findById(healthCenterId)
                .orElseThrow(() -> new RecordNotFoundException("Posto de saúde não encontrado com o id: " + healthCenterId));

        return fetchAppointmentsGroup(specialty, healthCenter);
    }

    private List<AppointmentsByDateDTO> fetchAppointmentsGroup(Specialty specialty, HealthCenter healthCenter) {
        List<AppointmentsByDateDTO> AppointmentsByDateDTOList = new ArrayList<>();        
        List<LocalDate> dates = appointmentRepository.findDistinctDates(specialty.getId(), healthCenter.getId())
                .stream()
                .map(java.sql.Date::toLocalDate)
                .toList();

        for (LocalDate date : dates) {
            List<DoctorResponseDTO> doctorDTOList = new ArrayList<>();
            List<Doctor> doctors = appointmentRepository.findDoctorsByDate(date, specialty.getId(), healthCenter.getId());

            for (Doctor doctor : doctors) {
                List<AppointmentResponseDTO> appointmentDTOList = appointmentRepository.findByDateAndDoctor(
                    date, doctor.getId(), specialty.getId(), healthCenter.getId())
                        .stream()
                        .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.PENDING && appointment.getUser() == null)
                        .map(appointmentMapper::toDTO)
                        .toList();

                if (!appointmentDTOList.isEmpty()) {
                    DoctorResponseDTO doctorDTO = doctorMapper.toDTOwithAppointments(doctor, appointmentDTOList);
                    doctorDTOList.add(doctorDTO);
                }
            }

            if (!doctorDTOList.isEmpty()) {
                String dateString = date.format(dateFormatter);
                AppointmentsByDateDTO appointmentsByDateDTO = new AppointmentsByDateDTO(dateString, doctorDTOList);
                AppointmentsByDateDTOList.add(appointmentsByDateDTO);
            }
        }

        return AppointmentsByDateDTOList;
    }

    public List<YearsWithAppointmentsDTO> getYearsWithAppointments() {
        List<YearsWithAppointmentsDTO> yearsWithAppointmentsDTOList = new ArrayList<>();
        List<Integer> years = appointmentRepository.findDistinctYears();

        for (Integer year : years) {
            List<MonthlyAppointmentStatsDTO> monthlyStats = new ArrayList<>();

            List<Object[]> scheduledResults = appointmentRepository.countScheduledAppointmentsMonth(year);
            List<Object[]> attendedResults = appointmentRepository.countAppointmentsByStatusAndMonth(year, "attended");
            List<Object[]> missedResults = appointmentRepository.countAppointmentsByStatusAndMonth(year, "missed");
            List<Object[]> cancelledResults = appointmentRepository.countCancelledAppointmentsByMonth(year);

            for (int month = 1; month <= 12; month++) {
                int scheduledCount = 0;
                int attendedCount = 0; 
                int missedCount = 0; 
                int cancelledCount = 0;

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

                monthlyStats.add(new MonthlyAppointmentStatsDTO(month, scheduledCount, attendedCount, missedCount, cancelledCount));
            }

            yearsWithAppointmentsDTOList.add(new YearsWithAppointmentsDTO(year, monthlyStats));
        }

        return yearsWithAppointmentsDTOList;
    }

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

    public AppointmentResponseDTO findAppointmentById(@NotNull @Positive Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public AppointmentResponseDTO createAppointment(@Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentMapper.toDTO(appointmentRepository.save(appointmentMapper.toEntity(appointmentDTO)));
    }

    public AppointmentResponseDTO updateAppointment(@NotNull @Positive Long id, @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setAppointmentDateTime(appointmentMapper.parseDateTime(appointmentDTO.appointmentDateTime()));
                    recordFound.setAppointmentStatus(appointmentMapper.convertAppointmentStatusValue(appointmentDTO.appointmentStatus()));

                    return appointmentMapper.toDTO(appointmentRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

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

    public AppointmentResponseDTO cancelAppointment(@NotNull @Positive Long id) {
        return appointmentRepository.findById(id)
                .map(recordFound -> {
                    if (recordFound.getAppointmentStatus() == AppointmentStatus.SCHEDULED) {
                        recordFound.setUser(null);
                        recordFound.setAppointmentStatus(AppointmentStatus.PENDING);
                        recordFound.setCancellationCount(recordFound.getCancellationCount() + 1);
                    } else {
                        throw new AppointmentCancelException("Não é possível desmarcar uma consulta com o estado: " + recordFound.getAppointmentStatus());
                    }

                    return appointmentMapper.toDTO(appointmentRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deleteAppointment(@NotNull @Positive Long id) {
        appointmentRepository.delete(appointmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
