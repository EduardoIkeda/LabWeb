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

    /**
     * Converte uma entidade Doctor para um DTO de resposta (DoctorResponseDTO).
     * @param doctor A entidade Doctor a ser convertida.
     * @return O DoctorResponseDTO correspondente.
     */
    public DoctorResponseDTO toDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        // Recupera o nome do médico da base de dados
        String doctorName = doctorRepository.getDoctorName(doctor.getId());

        // Formata o horário de início e fim de trabalho do médico
        String startTime = doctor.getStartWork().format(FORMATTER_BR);
        String endTime = doctor.getEndWork().format(FORMATTER_BR);

        // Converte os dias de trabalho para uma lista de nomes de dias
        List<String> workingDays = doctor.getWorkingDays()
                .stream()
                .map(Enum::name)
                .toList();

        // Cria a lista de agendamentos do médico (inicialmente vazia)
        List<AppointmentResponseDTO> doctorAppointments = new ArrayList<>();

        // Retorna o DTO com os dados do médico
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

    /**
     * Converte um DTO de Doctor (DoctorDTO) para a entidade Doctor.
     * @param doctorDTO O DTO de Doctor a ser convertido.
     * @return A entidade Doctor correspondente.
     */
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
     * Converte uma lista de strings que representam os dias da semana para um conjunto de DayOfWeek.
     * @param daysOfWeekStrings Lista de strings representando os dias da semana.
     * @return Um Set de DayOfWeek com os dias de trabalho.
     * @throws InvalidDayOfWeekException Se algum valor não for um dia válido.
     */
    public Set<DayOfWeek> convertToDayOfWeekSet(List<String> daysOfWeekStrings) {
        Set<DayOfWeek> workingDays = new HashSet<>();

        for (String dayString : daysOfWeekStrings) {
            try {
                // Converte a string para o enum DayOfWeek
                DayOfWeek day = DayOfWeek.valueOf(dayString.trim().toUpperCase());
                workingDays.add(day);  
            } catch (IllegalArgumentException e) {
                throw new InvalidDayOfWeekException(dayString, e);
            }
        }

        return workingDays;
    }

    /**
     * Converte uma entidade Doctor para um DTO de resposta (DoctorResponseDTO) com a lista de agendamentos.
     * @param doctor A entidade Doctor a ser convertida.
     * @param appointmentDTOs A lista de agendamentos do médico.
     * @return O DoctorResponseDTO correspondente, com a lista de agendamentos.
     */
    public DoctorResponseDTO toDTOwithAppointments(Doctor doctor, List<AppointmentResponseDTO> appointmentDTOs) {
        if (doctor == null) {
            return null;
        }

        // Recupera o nome do médico da base de dados
        String doctorName = doctorRepository.getDoctorName(doctor.getId());

        // Formata o horário de início e fim de trabalho do médico
        String startTime = doctor.getStartWork().format(FORMATTER_BR);
        String endTime = doctor.getEndWork().format(FORMATTER_BR);

        // Converte os dias de trabalho para uma lista de nomes de dias
        List<String> workingDays = doctor.getWorkingDays()
                .stream()
                .map(Enum::name)
                .toList();

        // Atribui a lista de agendamentos ao médico
        List<AppointmentResponseDTO> doctorAppointments = appointmentDTOs;

        // Retorna o DTO com os dados do médico e seus agendamentos
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
