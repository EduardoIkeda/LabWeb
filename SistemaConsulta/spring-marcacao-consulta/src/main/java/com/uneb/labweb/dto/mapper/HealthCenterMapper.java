package com.uneb.labweb.dto.mapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.HealthCenterDTO;
import com.uneb.labweb.dto.response.HealthCenterResponseDTO;
import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.model.HealthCenter;
import com.uneb.labweb.repository.AppointmentRepository;

@Component
public class HealthCenterMapper {

    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("HH:mm");
    private final AppointmentRepository appointmentRepository;

    public HealthCenterMapper(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public HealthCenterResponseDTO toDTO(HealthCenter healthCenter) {
        if (healthCenter == null) {
            return null;
        }

        String openingTime = healthCenter.getOpeningHour().format(FORMATTER_BR);
        String closingTime = healthCenter.getClosingHour().format(FORMATTER_BR);

        //List<Appointment> healthCenterAppointments = healthCenter.getAppointments();
        //long appointmentsCount = healthCenterAppointments.size();
        long availableAppointmentsCount = healthCenter.getAppointments()
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

    public HealthCenterResponseDTO toDTOwithAppointmentFilter(HealthCenter healthCenter, Long specialtyId) {
        if (healthCenter == null) {
            return null;
        }

        String openingTime = healthCenter.getOpeningHour().format(FORMATTER_BR);
        String closingTime = healthCenter.getClosingHour().format(FORMATTER_BR);

        long availableAppointmentsCount = appointmentRepository.findBySpecialtyAndHealthCenter(specialtyId, healthCenter.getId())
                .stream()
                .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.PENDING && appointment.getUser() == null)
                .count();

        return new HealthCenterResponseDTO(healthCenter.getId(), healthCenter.getName(), healthCenter.getAddress(), openingTime, closingTime, availableAppointmentsCount);
    }
}
