package com.uneb.labweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.mapper.AppointmentMapper;
import com.uneb.labweb.dto.request.AppointmentDTO;
import com.uneb.labweb.dto.response.AppointmentResponseDTO;
import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.model.User;
import com.uneb.labweb.repository.AppointmentRepository;
import com.uneb.labweb.repository.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            AppointmentMapper appointmentMapper,
            UserRepository userRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.userRepository = userRepository;
    }

    public List<AppointmentResponseDTO> findAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDTO)
                .toList();
    }

    public AppointmentResponseDTO findAppointmentById(@NotNull @Positive Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public AppointmentResponseDTO createAppointment(@Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentMapper.toDTO(appointmentRepository.save(appointmentMapper.toEntity(appointmentDTO)));
    }

    public AppointmentResponseDTO updateAppointment(@NotNull @Positive Long id, @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setAppointmentDateTime(appointmentMapper.parseDateTime(appointmentDTO.appointmentDateTime()));
                    recordFound.setAppointmentStatus(appointmentMapper.convertAppointmentStatusValue(appointmentDTO.appointmentStatus()));

                    return appointmentMapper.toDTO(appointmentRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public AppointmentResponseDTO scheduleAppointment(@NotNull @Positive Long id, @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentRepository.findById(id)
                .map(recordFound -> {
                    Optional<User> user = userRepository.findById(appointmentDTO.patientId());

                    if (user.isEmpty()) {
                        throw new RecordNotFoundException("Usuário não encontrado com o id: " + appointmentDTO.patientId());
                    }

                    recordFound.setUser(user.get());
                    recordFound.setAppointmentStatus(AppointmentStatus.SCHEDULED);

                    return appointmentMapper.toDTO(appointmentRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deleteAppointment(@NotNull @Positive Long id) {
        appointmentRepository.delete(appointmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
