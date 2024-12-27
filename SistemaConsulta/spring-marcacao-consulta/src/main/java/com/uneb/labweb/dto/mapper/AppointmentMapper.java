package com.uneb.labweb.dto.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.AppointmentDTO;
import com.uneb.labweb.dto.response.AppointmentResponseDTO;
import com.uneb.labweb.dto.response.PartialAppointmentDTO;
import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.model.Appointment;
import com.uneb.labweb.repository.AppointmentRepository;

@Component
public class AppointmentMapper {

    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final AppointmentRepository appointmentRepository;

    public AppointmentMapper(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Converte uma entidade Appointment para um DTO de resposta (AppointmentResponseDTO).
     * @param appointment A entidade Appointment a ser convertida.
     * @return O AppointmentResponseDTO correspondente.
     */
    public AppointmentResponseDTO toDTO(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        // Formata a data e hora do agendamento
        String appointmentDateTime = appointment.getAppointmentDateTime().format(FORMATTER_BR);
        
        // Recupera dados adicionais relacionados ao agendamento
        PartialAppointmentDTO appointmentData = appointmentRepository.getAppointmentData(appointment.getId());

        // Extrai dados do agendamento (caso existam)
        Long patientId = appointmentData != null ? appointmentData.patientId() : null;
        Long healthCenterId = appointmentData != null ? appointmentData.healthCenterId() : null;
        Long specialtyId = appointmentData != null ? appointmentData.specialtyId() : null;
        String doctorName = appointmentData != null ? appointmentData.doctorName() : null;
        String specialtyName = appointmentData != null ? appointmentData.specialtyName() : null;
        String healthCenterName = appointmentData != null ? appointmentData.healthCenterName() : null;
        String healthCenterAddress = appointmentData != null ? appointmentData.healthCenterAddress() : null;

        // Determina se o agendamento é amanhã e se já foi finalizado
        boolean isTomorrow = false;
        boolean isFinalized = false;

        LocalDate appointmentDate = appointment.getAppointmentDateTime().toLocalDate();
        LocalDate today = LocalDate.now();
        if (appointmentDate.isEqual(today.plusDays(1))) {
            isTomorrow = true;
        }

        if ((appointment.getAppointmentStatus() == AppointmentStatus.ATTENDED) || (appointment.getAppointmentStatus() == AppointmentStatus.MISSED)) {
            isFinalized = true;
        }

        // Retorna o DTO com os dados processados
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointmentDateTime,
                appointment.getAppointmentStatus().getValue(),
                patientId,
                healthCenterId,
                specialtyId,
                doctorName,
                specialtyName,
                healthCenterName,
                healthCenterAddress,
                isTomorrow,
                isFinalized
        );
    }

    /**
     * Converte um DTO de agendamento (AppointmentDTO) para a entidade Appointment.
     * @param appointmentDTO O DTO de agendamento a ser convertido.
     * @return A entidade Appointment correspondente.
     */
    public Appointment toEntity(AppointmentDTO appointmentDTO) {
        if (appointmentDTO == null) {
            return null;
        }

        Appointment appointment = new Appointment();

        if (appointmentDTO.id() != null) {
            appointment.setId(appointmentDTO.id());
        }
        appointment.setAppointmentDateTime(parseDateTime(appointmentDTO.appointmentDateTime()));
        appointment.setAppointmentStatus(convertAppointmentStatusValue(appointmentDTO.appointmentStatus()));

        return appointment;
    }

    /**
     * Converte uma string de data/hora no formato "dd/MM/yyyy HH:mm" para LocalDateTime.
     * @param dateTimeString A string de data/hora a ser convertida.
     * @return O LocalDateTime correspondente.
     * @throws InvalidDateTimeException Se a conversão falhar.
     */
    public LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, FORMATTER_BR);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException(dateTimeString, e);
        }
    }

    /**
     * Converte o valor de status de agendamento para o enum AppointmentStatus.
     * @param value O valor de status a ser convertido.
     * @return O enum AppointmentStatus correspondente.
     * @throws IllegalArgumentException Se o valor de status for inválido.
     */
    public AppointmentStatus convertAppointmentStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "pending" -> AppointmentStatus.PENDING;
            case "scheduled" -> AppointmentStatus.SCHEDULED;
            case "attended" -> AppointmentStatus.ATTENDED;
            case "missed" -> AppointmentStatus.MISSED;
            default -> throw new IllegalArgumentException("Status inválido: " + value);
        };
    }
}
