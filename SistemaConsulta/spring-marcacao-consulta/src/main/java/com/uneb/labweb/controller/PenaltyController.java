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

import com.uneb.labweb.dto.PenaltyDTO;
import com.uneb.labweb.service.PenaltyService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/penalties")
public class PenaltyController {

    private final PenaltyService penaltyService;

    public PenaltyController(PenaltyService penaltyService) {
        this.penaltyService = penaltyService;
    }

    @GetMapping
    public List<PenaltyDTO> findAllPenalties() {
        return penaltyService.findAllPenalties();
    }

    @GetMapping("/{id}")
    public PenaltyDTO findPenaltyById(@PathVariable @NotNull @Positive Long id) {
        return penaltyService.findPenaltyById(id);
    }
    
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PenaltyDTO createPenalty(@RequestBody @Valid @NotNull PenaltyDTO penaltyDTO) {
        return penaltyService.createPenalty(penaltyDTO);    
    }

    @PutMapping("/{id}")
    public PenaltyDTO updatePenalty(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull PenaltyDTO penaltyDTO) {
        return penaltyService.updatePenalty(id, penaltyDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePenalty(@PathVariable @NotNull @Positive Long id) {
        penaltyService.deletePenalty(id);
    }
}