package com.uneb.labweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.SpecialtyDTO;
import com.uneb.labweb.dto.mapper.SpecialtyMapper;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.repository.SpecialtyRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyService(SpecialtyRepository specialtyRepository, SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
    }

    public List<SpecialtyDTO> findAllSpecialties() {
        return specialtyRepository.findAll()
                .stream()
                .map(specialtyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SpecialtyDTO findSpecialtyById(@NotNull @Positive Long id) {
        return specialtyRepository.findById(id)
                .map(specialtyMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public SpecialtyDTO createSpecialty(@Valid @NotNull SpecialtyDTO specialtyDTO) {
        return specialtyMapper.toDTO(specialtyRepository.save(specialtyMapper.toEntity(specialtyDTO)));
    }

    public SpecialtyDTO updateSpecialty(@NotNull @Positive Long id, @Valid @NotNull SpecialtyDTO specialtyDTO) {
        return specialtyRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(specialtyDTO.name());
                    recordFound.setDescription(specialtyDTO.description());

                    // ...
                    
                    return specialtyMapper.toDTO(specialtyRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deleteSpecialty(@NotNull @Positive Long id) {
        specialtyRepository.delete(specialtyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
