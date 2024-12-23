package com.uneb.labweb.dto.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.AppointmentDTO;
import com.uneb.labweb.dto.response.AppointmentResponseDTO;
import com.uneb.labweb.dto.response.TempDTO;
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

    public AppointmentResponseDTO toDTO(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        String appointmentDateTime = appointment.getAppointmentDateTime().format(FORMATTER_BR);
        TempDTO appointmentData = appointmentRepository.getAppointmentData(appointment.getId());

        Long patientId = appointmentData.patientId();
        String doctorName = appointmentData.doctorName();
        String specialtyName = appointmentData.specialtyName();
        String healthCenterName = appointmentData.healthCenterName();
        String healthCenterAddress = appointmentData.healthCenterAddress();

        return new AppointmentResponseDTO(
            appointment.getId(), 
            appointmentDateTime, 
            appointment.getAppointmentStatus().getValue(),
            patientId,
            doctorName,
            specialtyName,
            healthCenterName,
            healthCenterAddress
        ); 
    }
    
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

    public LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, FORMATTER_BR);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException(dateTimeString, e);
        }
    }

    public AppointmentStatus convertAppointmentStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "pending" -> AppointmentStatus.PENDING;
            case "scheduled" -> AppointmentStatus.SCHEDULED;
            case "attended" -> AppointmentStatus.ATTENDED;
            case "missed" -> AppointmentStatus.MISSED;
            case "canceled" -> AppointmentStatus.CANCELED;
            default -> throw new IllegalArgumentException("Status inválido: " + value);
        };    
    }
}
