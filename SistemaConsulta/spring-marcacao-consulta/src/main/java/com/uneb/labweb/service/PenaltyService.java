package com.uneb.labweb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.PenaltyDTO;
import com.uneb.labweb.dto.mapper.PenaltyMapper;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.repository.PenaltyRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;
    private final PenaltyMapper penaltyMapper;

    public PenaltyService(PenaltyRepository penaltyRepository, PenaltyMapper penaltyMapper) {
        this.penaltyRepository = penaltyRepository;
        this.penaltyMapper = penaltyMapper;
    }

    public List<PenaltyDTO> findAllPenalties() {
        return penaltyRepository.findAll()
                .stream()
                .map(penaltyMapper::toDTO)
                .toList();
    }

    public PenaltyDTO findPenaltyById(@NotNull @Positive Long id) {
        return penaltyRepository.findById(id)
                .map(penaltyMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public PenaltyDTO createPenalty(@Valid @NotNull PenaltyDTO penaltyDTO) {
        return penaltyMapper.toDTO(penaltyRepository.save(penaltyMapper.toEntity(penaltyDTO)));
    }

    public PenaltyDTO updatePenalty(@NotNull @Positive Long id, @Valid @NotNull PenaltyDTO penaltyDTO) {
        return penaltyRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setPenaltyStartDate(penaltyMapper.parseDate(penaltyDTO.penaltyStartDate()));
                    recordFound.setPenaltyEndDate(penaltyMapper.parseDate(penaltyDTO.penaltyEndDate()));

                    return penaltyMapper.toDTO(penaltyRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deletePenalty(@NotNull @Positive Long id) {
        penaltyRepository.delete(penaltyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
