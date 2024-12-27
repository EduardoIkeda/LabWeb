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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uneb.labweb.dto.request.HealthCenterDTO;
import com.uneb.labweb.dto.response.HealthCenterResponseDTO;
import com.uneb.labweb.service.HealthCenterService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/health-centers")
public class HealthCenterController {

    private final HealthCenterService healthCenterService;

    public HealthCenterController(HealthCenterService healthCenterService) {
        this.healthCenterService = healthCenterService;
    }

    /**
     * Retorna a lista de todos os postos de saúde.
     */
    @GetMapping
    public List<HealthCenterResponseDTO> findAllHealthCenters() {
        return healthCenterService.findAllHealthCenters();
    }

    /**
     * Retorna os postos de saúde que oferecem uma especialidade específica.
     */
    @GetMapping("/by-specialty/{id}")
    public List<HealthCenterResponseDTO> findHealthCentersBySpecialty(@PathVariable @NotNull @Positive Long id) {
        return healthCenterService.findHealthCentersBySpecialty(id);
    }

    /**
     * Retorna os detalhes de um posto de saúde específico pelo ID.
     */
    @GetMapping("/{id}")
    public HealthCenterResponseDTO findHealthCenterById(@PathVariable @NotNull @Positive Long id) {
        return healthCenterService.findHealthCenterById(id);
    }

    /**
     * Cria um novo posto de saúde.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public HealthCenterResponseDTO createHealthCenter(@RequestBody @Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterService.createHealthCenter(healthCenterDTO);
    }

    /**
     * Atualiza os dados de um posto de saúde existente pelo ID.
     */
    @PutMapping("/{id}")
    public HealthCenterResponseDTO updateHealthCenter(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterService.updateHealthCenter(id, healthCenterDTO);
    }

    /**
     * Adiciona médicos a um posto de saúde específico.
     */
    @PatchMapping("/{id}")
    public HealthCenterResponseDTO addDoctors(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterService.addDoctors(id, healthCenterDTO);
    }

    /**
     * Exclui um posto de saúde específico pelo ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteHealthCenter(@PathVariable @NotNull @Positive Long id) {
        healthCenterService.deleteHealthCenter(id);
    }
}