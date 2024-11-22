package com.uneb.labweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.AppointmentDTO;
import com.uneb.labweb.dto.mapper.AppointmentMapper;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.repository.AppointmentRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    public List<AppointmentDTO> findAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDTO findAppointmentById(@NotNull @Positive Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public AppointmentDTO createAppointment(@Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentMapper.toDTO(appointmentRepository.save(appointmentMapper.toEntity(appointmentDTO)));
    }

    public AppointmentDTO updateAppointment(@NotNull @Positive Long id, @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setAppointmentDateTime(appointmentMapper.parseDateTime(appointmentDTO.appointmentDateTime()));
                    recordFound.setAttended(appointmentDTO.attended());

                    return appointmentMapper.toDTO(appointmentRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deleteAppointment(@NotNull @Positive Long id) {
        appointmentRepository.deleteById(id);
    }
}
