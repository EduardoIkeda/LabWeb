package com.uneb.labweb.dto.mapper;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.DoctorDTO;
import com.uneb.labweb.dto.response.AppointmentResponseDTO;
import com.uneb.labweb.dto.response.DoctorResponseDTO;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.exception.InvalidDayOfWeekException;
import com.uneb.labweb.model.Doctor;
import com.uneb.labweb.repository.DoctorRepository;

@Component
public class DoctorMapper {

    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("HH:mm");
    private final DoctorRepository doctorRepository;

    public DoctorMapper(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorResponseDTO toDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        String doctorName = doctorRepository.getDoctorName(doctor.getId());

        String startTime = doctor.getStartWork().format(FORMATTER_BR);
        String endTime = doctor.getEndWork().format(FORMATTER_BR);

        List<String> workingDays = doctor.getWorkingDays()
                .stream()
                .map(Enum::name)
                .toList();

        List<AppointmentResponseDTO> doctorAppointments = new ArrayList<>();

        return new DoctorResponseDTO(
            doctor.getId(),
            doctorName, 
            doctor.getCrm(), 
            startTime, 
            endTime, 
            workingDays,
            doctorAppointments
        );
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

    public DoctorResponseDTO toDTOwithAppointments(Doctor doctor, List<AppointmentResponseDTO> appointmentDTOs) {
        if (doctor == null) {
            return null;
        }

        String doctorName = doctorRepository.getDoctorName(doctor.getId());

        String startTime = doctor.getStartWork().format(FORMATTER_BR);
        String endTime = doctor.getEndWork().format(FORMATTER_BR);

        List<String> workingDays = doctor.getWorkingDays()
                .stream()
                .map(Enum::name)
                .toList();

        List<AppointmentResponseDTO> doctorAppointments = appointmentDTOs;

        return new DoctorResponseDTO(
            doctor.getId(),
            doctorName, 
            doctor.getCrm(), 
            startTime, 
            endTime, 
            workingDays,
            doctorAppointments
        );
    }
}
