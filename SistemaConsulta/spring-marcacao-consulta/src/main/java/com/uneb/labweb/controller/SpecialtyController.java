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

import com.uneb.labweb.dto.request.SpecialtyDTO;
import com.uneb.labweb.dto.response.SpecialtyCountDTO;
import com.uneb.labweb.service.SpecialtyService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public List<SpecialtyDTO> findAllSpecialties() {
        return specialtyService.findAllSpecialties();         
    }

    @GetMapping("/{id}")
    public SpecialtyDTO findSpecialtyById(@PathVariable @NotNull @Positive Long id) {
        return specialtyService.findSpecialtyById(id); 
    }

    @GetMapping("/count/{year}")
    public List<SpecialtyCountDTO> getSpecialtyAppointmentsCountByYear(@PathVariable @NotNull @Positive Long year) {
        return specialtyService.getSpecialtyAppointmentsCountByYear(year); 
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SpecialtyDTO createSpecialty(@RequestBody @Valid @NotNull SpecialtyDTO specialtyDTO) {
        return specialtyService.createSpecialty(specialtyDTO);        
    }

    @PutMapping("/{id}")
    public SpecialtyDTO updateSpecialty(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull SpecialtyDTO specialtyDTO) {
        return specialtyService.updateSpecialty(id, specialtyDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteSpecialty(@PathVariable @NotNull @Positive Long id) {
        specialtyService.deleteSpecialty(id);  
    }
}