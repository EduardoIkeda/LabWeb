package com.uneb.labweb.dto.mapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.HealthCenterDTO;
import com.uneb.labweb.dto.response.HealthCenterResponseDTO;
import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.model.Appointment;
import com.uneb.labweb.model.HealthCenter;

@Component
public class HealthCenterMapper {

    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("HH:mm");

    public HealthCenterResponseDTO toDTO(HealthCenter healthCenter) {
        if (healthCenter == null) {
            return null;
        }

        String openingTime = healthCenter.getOpeningHour().format(FORMATTER_BR);
        String closingTime = healthCenter.getClosingHour().format(FORMATTER_BR);

        List<Appointment> healthCenterAppointments = healthCenter.getAppointments();
        //long appointmentsCount = healthCenterAppointments.size();
        long availableAppointmentsCount = healthCenterAppointments
                .stream()
                .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.PENDING && appointment.getUser() == null)
                .count();

        return new HealthCenterResponseDTO(healthCenter.getId(), healthCenter.getName(), healthCenter.getAddress(), openingTime, closingTime, availableAppointmentsCount);
    }

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

        return healthCenter;
    }

    public LocalTime parseTime(String timeString) {
        try {
            return LocalTime.parse(timeString, FORMATTER_BR);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException(timeString, e);
        }
    }
}
