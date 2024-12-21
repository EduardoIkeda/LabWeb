package com.uneb.labweb.dto.mapper;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.DoctorDTO;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.exception.InvalidDayOfWeekException;
import com.uneb.labweb.model.Doctor;

@Component
public class DoctorMapper {

    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("HH:mm");

    public DoctorDTO toDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        String startTime = doctor.getStartWork().format(FORMATTER_BR);
        String endTime = doctor.getEndWork().format(FORMATTER_BR);

        List<String> workingDays = doctor.getWorkingDays()
                .stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        return new DoctorDTO(doctor.getId(), doctor.getCrm(), startTime, endTime, workingDays);
    }

    public Doctor toEntity(DoctorDTO doctorDTO) {
        if (doctorDTO == null) {
            return null;
        }

        Doctor doctor = new Doctor();

        if (doctorDTO.id() != null) {
            doctor.setId(doctorDTO.id());
        }
        doctor.setCrm(doctorDTO.crm());
        doctor.setStartWork(parseTime(doctorDTO.startWork()));
        doctor.setEndWork(parseTime(doctorDTO.endWork()));
        doctor.setWorkingDays(convertToDayOfWeekSet(doctorDTO.workingDays()));

        return doctor;
    }

    public LocalTime parseTime(String timeString) {
        try {
            return LocalTime.parse(timeString, FORMATTER_BR);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException(timeString, e);
        }
    }

    public Set<DayOfWeek> convertToDayOfWeekSet(List<String> daysOfWeekStrings) {
        Set<DayOfWeek> workingDays = new HashSet<>();

        for (String dayString : daysOfWeekStrings) {
            try {
                DayOfWeek day = DayOfWeek.valueOf(dayString.trim().toUpperCase());
                workingDays.add(day);  
            } catch (IllegalArgumentException e) {
                throw new InvalidDayOfWeekException(dayString, e);
            }
        }

        return workingDays;
    }
}
