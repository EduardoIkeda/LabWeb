package com.uneb.labweb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.mapper.DoctorMapper;
import com.uneb.labweb.dto.request.DoctorDTO;
import com.uneb.labweb.dto.response.DoctorResponseDTO;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.repository.DoctorRepository;
import com.uneb.labweb.repository.HealthCenterRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final HealthCenterRepository healthCenterRepository;

    public DoctorService(
        DoctorRepository doctorRepository, 
        DoctorMapper doctorMapper, 
        HealthCenterRepository healthCenterRepository
    ) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.healthCenterRepository = healthCenterRepository;
    }

    public List<DoctorResponseDTO> findAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    public List<DoctorResponseDTO> findDoctorsByHealthCenter(@NotNull @Positive Long healthCenterId) {
        return healthCenterRepository.findById(healthCenterId)
                .map(recordFound -> {
                    return doctorRepository.findByHealthCenterId(healthCenterId)
                            .stream()
                            .map(doctorMapper::toDTO)
                            .toList();
                })
                .orElseThrow(() -> new RecordNotFoundException("Posto de saúde não encontrado com o id: " + healthCenterId));
    }

    public DoctorResponseDTO findDoctorById(@NotNull @Positive Long id) { 
        return doctorRepository.findById(id)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public DoctorResponseDTO createDoctor(@Valid @NotNull DoctorDTO doctorDTO) {
        return doctorMapper.toDTO(doctorRepository.save(doctorMapper.toEntity(doctorDTO)));
    }

    public DoctorResponseDTO updateDoctor(@NotNull @Positive Long id, @Valid @NotNull DoctorDTO doctorDTO) {
        return doctorRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setCrm(doctorDTO.crm());
                    recordFound.setStartWork(doctorMapper.parseTime(doctorDTO.startWork()));
                    recordFound.setEndWork(doctorMapper.parseTime(doctorDTO.endWork()));
                    recordFound.setWorkingDays(doctorMapper.convertToDayOfWeekSet(doctorDTO.workingDays()));

                    return doctorMapper.toDTO(doctorRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deleteDoctor(@NotNull @Positive Long id) {
        doctorRepository.delete(doctorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
