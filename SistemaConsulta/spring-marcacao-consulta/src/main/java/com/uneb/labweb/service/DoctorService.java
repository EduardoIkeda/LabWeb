package com.uneb.labweb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.DoctorDTO;
import com.uneb.labweb.dto.mapper.DoctorMapper;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.repository.DoctorRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    public List<DoctorDTO> findAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO findDoctorById(@NotNull @Positive Long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public DoctorDTO createDoctor(@Valid @NotNull DoctorDTO doctorDTO) {
        return doctorMapper.toDTO(doctorRepository.save(doctorMapper.toEntity(doctorDTO)));
    }

    public DoctorDTO updateDoctor(@NotNull @Positive Long id, @Valid @NotNull DoctorDTO doctorDTO) {
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
