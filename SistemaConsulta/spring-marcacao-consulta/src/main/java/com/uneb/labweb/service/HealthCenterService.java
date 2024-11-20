package com.uneb.labweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.HealthCenterDTO;
import com.uneb.labweb.dto.mapper.HealthCenterMapper;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.repository.HealthCenterRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class HealthCenterService {

    private final HealthCenterRepository healthCenterRepository;
    private final HealthCenterMapper healthCenterMapper;

    public HealthCenterService(HealthCenterRepository healthCenterRepository, HealthCenterMapper healthCenterMapper) {
        this.healthCenterRepository = healthCenterRepository;
        this.healthCenterMapper = healthCenterMapper;
    }

    public List<HealthCenterDTO> findAllHealthCenters() {
        return healthCenterRepository.findAll()
                .stream()
                .map(healthCenterMapper::toDTO)
                .collect(Collectors.toList());
    }

    public HealthCenterDTO findHealthCenterById(@NotNull @Positive Long id) {
        return healthCenterRepository.findById(id)
                .map(healthCenterMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public HealthCenterDTO createHealthCenter(@Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterMapper.toDTO(healthCenterRepository.save(healthCenterMapper.toEntity(healthCenterDTO)));
    }

    public HealthCenterDTO updateHealthCenter(@NotNull @Positive Long id, @Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(healthCenterDTO.name());
                    recordFound.setAddress(healthCenterDTO.address());
                    recordFound.setOperatingHours(healthCenterDTO.operatingHours());
                    recordFound.setSpecialties(healthCenterDTO.specialties());

                    return healthCenterMapper.toDTO(healthCenterRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deleteHealthCenter(@NotNull @Positive Long id) {
        healthCenterRepository.delete(healthCenterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
