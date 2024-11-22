package com.uneb.labweb.dto.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.AppointmentDTO;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.model.Appointment;

@Component
public class AppointmentMapper {

    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public AppointmentDTO toDTO(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        String dateTime = appointment.getAppointmentDateTime().format(FORMATTER_BR);
        return new AppointmentDTO(appointment.getId(), dateTime, appointment.getAttended());
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
        appointment.setAttended(appointmentDTO.attended());
        
        return appointment;
    }

    public LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, FORMATTER_BR);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException(dateTimeString, e);
        }
    }
}
