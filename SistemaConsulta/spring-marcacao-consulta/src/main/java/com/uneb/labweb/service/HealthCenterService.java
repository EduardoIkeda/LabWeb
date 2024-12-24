package com.uneb.labweb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.mapper.HealthCenterMapper;
import com.uneb.labweb.dto.request.HealthCenterDTO;
import com.uneb.labweb.dto.response.HealthCenterResponseDTO;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.repository.HealthCenterRepository;
import com.uneb.labweb.repository.SpecialtyRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class HealthCenterService {

    private final HealthCenterRepository healthCenterRepository;
    private final HealthCenterMapper healthCenterMapper;
    private final SpecialtyRepository specialtyRepository;

    public HealthCenterService(
            HealthCenterRepository healthCenterRepository,
            HealthCenterMapper healthCenterMapper,
            SpecialtyRepository specialtyRepository
    ) {
        this.healthCenterRepository = healthCenterRepository;
        this.healthCenterMapper = healthCenterMapper;
        this.specialtyRepository = specialtyRepository;

    }

    public List<HealthCenterResponseDTO> findAllHealthCenters() {
        return healthCenterRepository.findAll()
                .stream()
                .map(healthCenterMapper::toDTO)
                .toList();
    }

    public List<HealthCenterResponseDTO> findHealthCentersBySpecialty(@NotNull @Positive Long specialtyId) {
        return specialtyRepository.findById(specialtyId)
                .map(recordFound -> {
                    return healthCenterRepository.findBySpecialtyId(specialtyId)
                            .stream()
                            .map(healthCenter -> healthCenterMapper.toDTOwithAppointmentFilter(healthCenter, specialtyId))
                            .filter(healthCenterDTO -> healthCenterDTO.availableAppointmentsCount() > 0)
                            .toList();
                })
                .orElseThrow(() -> new RecordNotFoundException("Especialidade não encontrada com o id: " + specialtyId));
    }

    public HealthCenterResponseDTO findHealthCenterById(@NotNull @Positive Long id) {
        return healthCenterRepository.findById(id)
                .map(healthCenterMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public HealthCenterResponseDTO createHealthCenter(@Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterMapper.toDTO(healthCenterRepository.save(healthCenterMapper.toEntity(healthCenterDTO)));
    }

    public HealthCenterResponseDTO updateHealthCenter(@NotNull @Positive Long id, @Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(healthCenterDTO.name());
                    recordFound.setAddress(healthCenterDTO.address());
                    recordFound.setOpeningHour(healthCenterMapper.parseTime(healthCenterDTO.openingHour()));
                    recordFound.setClosingHour(healthCenterMapper.parseTime(healthCenterDTO.closingHour()));

                    return healthCenterMapper.toDTO(healthCenterRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deleteHealthCenter(@NotNull @Positive Long id) {
        healthCenterRepository.delete(healthCenterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
