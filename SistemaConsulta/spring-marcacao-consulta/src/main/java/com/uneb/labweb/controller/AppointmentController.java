package com.uneb.labweb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uneb.labweb.dto.AppointmentDTO;
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

    @GetMapping
    public List<AppointmentDTO> findAllAppointments() {
        return appointmentService.findAllAppointments();  
    }

    @GetMapping("/{id}")
    public AppointmentDTO findAppointmentById(@PathVariable @NotNull @Positive Long id) {
        return appointmentService.findAppointmentById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppointmentDTO createAppointment(@RequestBody @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentService.createAppointment(appointmentDTO);
    }

    @PutMapping("/{id}")
    public AppointmentDTO updateAppointment(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull AppointmentDTO appointmentDTO) {
        return appointmentService.updateAppointment(id, appointmentDTO);     
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable @NotNull @Positive Long id) {
        appointmentService.deleteAppointment(id);
    }
}