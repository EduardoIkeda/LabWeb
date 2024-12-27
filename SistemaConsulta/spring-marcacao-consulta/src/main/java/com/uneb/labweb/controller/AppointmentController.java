package com.uneb.labweb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uneb.labweb.dto.request.AppointmentDTO;
import com.uneb.labweb.dto.response.AppointmentResponseDTO;
import com.uneb.labweb.dto.response.AppointmentsByDateDTO;
import com.uneb.labweb.dto.response.YearsWithAppointmentsDTO;
import com.uneb.labweb.service.AppointmentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Retorna a lista de todos os agendamentos.
     */
    @GetMapping
    public List<AppointmentResponseDTO> findAllAppointments() {
        return appointmentService.findAllAppointments();  
    }

    /**
     * Retorna os agendamentos agrupados por data e médicos, de acordo com o posto de saúde e especialidade.
     */
    @GetMapping("/group")
    public List<AppointmentsByDateDTO> findAppointmentsGroup(
        @RequestParam @NotNull @Positive Long healthCenterId,
        @RequestParam @NotNull @Positive Long specialtyId
    ) {
        return appointmentService.findAppointmentsGroup(healthCenterId, specialtyId);
    }

    /**
     * Retorna os anos que possuem agendamentos registrados, assim como estatísticas mensais das consultas.
     */
    @GetMapping("/years-with-appointments")
    public List<YearsWithAppointmentsDTO> getYearsWithAppointments() {
        return appointmentService.getYearsWithAppointments();
    }

    /**
     * Retorna os agendamentos de um usuário específico.
     */
    @GetMapping("/by-user/{id}")
    public List<AppointmentResponseDTO> findAppointmentsByUser(@PathVariable @NotNull @Positive Long id) {
        return appointmentService.findAppointmentsByUser(id); 
    }

    /**
     * Retorna os detalhes de um agendamento específico pelo ID.
     */
    @GetMapping("/{id}")
    public AppointmentResponseDTO findAppointmentById(@PathVariable @NotNull @Positive Long id) {
        return appointmentService.findAppointmentById(id);
    }

    /**
     * Cria um novo agendamento.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppointmentResponseDTO createAppointment(@RequestBody @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentService.createAppointment(appointmentDTO);
    }

    /**
     * Atualiza um agendamento existente pelo ID.
     */
    @PutMapping("/{id}")
    public AppointmentResponseDTO updateAppointment(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentService.updateAppointment(id, appointmentDTO);     
    }

    /**
     * Agenda um agendamento específico.
     */
    @PatchMapping("/schedule/{id}")
    public AppointmentResponseDTO scheduleAppointment(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentService.scheduleAppointment(id, appointmentDTO);     
    }

    /**
     * Cancela um agendamento específico.
     */
    @PatchMapping("/cancel/{id}")
    public AppointmentResponseDTO cancelAppointment(@PathVariable @NotNull @Positive Long id) {
        return appointmentService.cancelAppointment(id);     
    }

    /**
     * Exclui um agendamento específico pelo ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable @NotNull @Positive Long id) {
        appointmentService.deleteAppointment(id);
    }
}