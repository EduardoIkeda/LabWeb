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

import com.uneb.labweb.dto.request.DoctorDTO;
import com.uneb.labweb.dto.response.DoctorResponseDTO;
import com.uneb.labweb.service.DoctorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<DoctorResponseDTO> findAllDoctors() {
        return doctorService.findAllDoctors();
    }

    @GetMapping("/{id}")
    public DoctorResponseDTO findDoctorById(@PathVariable @NotNull @Positive Long id) {
        return doctorService.findDoctorById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DoctorResponseDTO createDoctor(@RequestBody @Valid @NotNull DoctorDTO doctorDTO) {
        return doctorService.createDoctor(doctorDTO);
    }

    @PutMapping("/{id}")
    public DoctorResponseDTO updateDoctor(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull DoctorDTO doctorDTO) {
        return doctorService.updateDoctor(id, doctorDTO);  
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable @NotNull @Positive Long id) {
        doctorService.deleteDoctor(id);
    }
}
