package com.uneb.labweb.dto.mapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.HealthCenterDTO;
import com.uneb.labweb.dto.response.HealthCenterResponseDTO;
import com.uneb.labweb.dto.response.SpecialtyResponseDTO;
import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.model.HealthCenter;
import com.uneb.labweb.model.Specialty;
import com.uneb.labweb.repository.AppointmentRepository;
import com.uneb.labweb.repository.SpecialtyRepository;

@Component
public class HealthCenterMapper {

    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("HH:mm");
    private final AppointmentRepository appointmentRepository;
    private final SpecialtyRepository specialtyRepository;

    public HealthCenterMapper(AppointmentRepository appointmentRepository, SpecialtyRepository specialtyRepository) {
        this.appointmentRepository = appointmentRepository;
        this.specialtyRepository = specialtyRepository;
    }

    /**
     * Converte uma entidade HealthCenter para um DTO de resposta (HealthCenterResponseDTO).
     * @param healthCenter A entidade HealthCenter a ser convertida.
     * @return O HealthCenterResponseDTO correspondente.
     */
    public HealthCenterResponseDTO toDTO(HealthCenter healthCenter) {
        if (healthCenter == null) {
            return null;
        }

        // Formata o horário de abertura e fechamento do posto de saúde
        String openingTime = healthCenter.getOpeningHour().format(FORMATTER_BR);
        String closingTime = healthCenter.getClosingHour().format(FORMATTER_BR);

        // Recupera as especialidades oferecidas pelo posto de saúde
        List<SpecialtyResponseDTO> specialties = specialtyRepository.findByHealthCenterId(healthCenter.getId())
                .stream()
                .map(specialty -> new SpecialtyResponseDTO(specialty.getId(), specialty.getName()))
                .toList();

        // Conta o número de agendamentos pendentes e disponíveis (sem usuário associado)
        long availableAppointmentsCount = healthCenter.getAppointments()
                .stream()
                .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.PENDING && appointment.getUser() == null)
                .count();

        // Retorna o DTO com os dados do posto de saúde
        return new HealthCenterResponseDTO(
                healthCenter.getId(),
                healthCenter.getName(),
                healthCenter.getAddress(),
                openingTime,
                closingTime,
                specialties,
                availableAppointmentsCount
        );
    }

    /**
     * Converte um DTO de HealthCenter (HealthCenterDTO) para a entidade HealthCenter.
     * @param healthCenterDTO O DTO de HealthCenter a ser convertido.
     * @return A entidade HealthCenter correspondente.
     */
    public HealthCenter toEntity(HealthCenterDTO healthCenterDTO) {
        if (healthCenterDTO == null) {
            return null;
        }

        HealthCenter healthCenter = new HealthCenter();

        if (healthCenterDTO.id() != null) {
            healthCenter.setId(healthCenterDTO.id());
        }
        healthCenter.setName(healthCenterDTO.name());
        healthCenter.setAddress(healthCenterDTO.address());
        healthCenter.setOpeningHour(parseTime(healthCenterDTO.openingHour()));
        healthCenter.setClosingHour(parseTime(healthCenterDTO.closingHour()));

        // Associa as especialidades ao posto de saúde
        healthCenterDTO.specialtyIds().forEach(specialtyId -> {
            Specialty specialty = specialtyRepository.findById(specialtyId)
                    .orElseThrow(() -> new RecordNotFoundException("Especialidade não encontrada com o id: " + specialtyId));
            healthCenter.getSpecialties().add(specialty);
        });

        return healthCenter;
    }

    /**
     * Converte uma string de horário no formato "HH:mm" para LocalTime.
     * @param timeString A string de horário a ser convertida.
     * @return O LocalTime correspondente.
     * @throws InvalidDateTimeException Se a conversão falhar.
     */
    public LocalTime parseTime(String timeString) {
        try {
            return LocalTime.parse(timeString, FORMATTER_BR);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException(timeString, e);
        }
    }

    /**
     * Converte uma entidade HealthCenter para um DTO de resposta (HealthCenterResponseDTO) com filtro de agendamentos por especialidade.
     * @param healthCenter A entidade HealthCenter a ser convertida.
     * @param specialtyId O ID da especialidade para filtrar os agendamentos.
     * @return O HealthCenterResponseDTO correspondente, com a quantidade de agendamentos disponíveis filtrados.
     */
    public HealthCenterResponseDTO toDTOwithAppointmentFilter(HealthCenter healthCenter, Long specialtyId) {
        if (healthCenter == null) {
            return null;
        }

        // Formata o horário de abertura e fechamento do posto de saúde
        String openingTime = healthCenter.getOpeningHour().format(FORMATTER_BR);
        String closingTime = healthCenter.getClosingHour().format(FORMATTER_BR);

        // Recupera as especialidades oferecidas pelo posto de saúde
        List<SpecialtyResponseDTO> specialties = specialtyRepository.findByHealthCenterId(healthCenter.getId())
                .stream()
                .map(specialty -> new SpecialtyResponseDTO(specialty.getId(), specialty.getName()))
                .toList();

        // Conta o número de agendamentos pendentes e disponíveis (sem usuário associado) para uma especialidade específica
        long availableAppointmentsCount = appointmentRepository.findBySpecialtyAndHealthCenter(specialtyId, healthCenter.getId())
                .stream()
                .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.PENDING && appointment.getUser() == null)
                .count(); 

        // Retorna o DTO com os dados do posto de saúde e o número de agendamentos disponíveis
        return new HealthCenterResponseDTO(
                healthCenter.getId(),
                healthCenter.getName(),
                healthCenter.getAddress(),
                openingTime,
                closingTime,
                specialties,
                availableAppointmentsCount
        );
    }
}
