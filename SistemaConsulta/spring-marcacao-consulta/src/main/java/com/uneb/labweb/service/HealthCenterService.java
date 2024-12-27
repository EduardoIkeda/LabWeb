package com.uneb.labweb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.mapper.HealthCenterMapper;
import com.uneb.labweb.dto.request.HealthCenterDTO;
import com.uneb.labweb.dto.response.HealthCenterResponseDTO;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.model.Doctor;
import com.uneb.labweb.model.Specialty;
import com.uneb.labweb.repository.DoctorRepository;
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
    private final DoctorRepository doctorRepository;

    public HealthCenterService(
            HealthCenterRepository healthCenterRepository,
            HealthCenterMapper healthCenterMapper,
            SpecialtyRepository specialtyRepository,
            DoctorRepository doctorRepository
    ) {
        this.healthCenterRepository = healthCenterRepository;
        this.healthCenterMapper = healthCenterMapper;
        this.specialtyRepository = specialtyRepository;
        this.doctorRepository = doctorRepository;
    }

    /**
     * Retorna todos os postos de saúde cadastrados.
     * @return Lista de DTOs de postos de saúde
     */
    public List<HealthCenterResponseDTO> findAllHealthCenters() {
        return healthCenterRepository.findAll()
                .stream()
                .map(healthCenterMapper::toDTO)
                .toList();
    }

    /**
     * Retorna postos de saúde por especialidade, filtrando os que têm vagas disponíveis.
     * @param specialtyId ID da especialidade
     * @return Lista de postos de saúde
     * @throws RecordNotFoundException Se a especialidade não for encontrada
     */
    public List<HealthCenterResponseDTO> findHealthCentersBySpecialty(@NotNull @Positive Long specialtyId) {
        return specialtyRepository.findById(specialtyId)
                .map(recordFound -> healthCenterRepository.findBySpecialtyId(specialtyId)
                        .stream()
                        .map(healthCenter -> healthCenterMapper.toDTOwithAppointmentFilter(healthCenter, specialtyId))
                        .filter(healthCenterDTO -> healthCenterDTO.availableAppointmentsCount() > 0)
                        .toList())
                .orElseThrow(() -> new RecordNotFoundException("Especialidade não encontrada com o id: " + specialtyId));
    }

    /**
     * Retorna um posto de saúde pelo ID.
     * @param id ID do posto de saúde
     * @return DTO do posto de saúde
     * @throws RecordNotFoundException Se o posto de saúde não for encontrado
     */
    public HealthCenterResponseDTO findHealthCenterById(@NotNull @Positive Long id) {
        return healthCenterRepository.findById(id)
                .map(healthCenterMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    /**
     * Cria um novo posto de saúde.
     * @param healthCenterDTO Dados do novo posto de saúde
     * @return DTO do posto de saúde criado
     */
    public HealthCenterResponseDTO createHealthCenter(@Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterMapper.toDTO(healthCenterRepository.save(healthCenterMapper.toEntity(healthCenterDTO)));
    }

    /**
     * Atualiza um posto de saúde existente.
     * @param id ID do posto de saúde
     * @param healthCenterDTO Dados atualizados do posto de saúde
     * @return DTO do posto de saúde atualizado
     * @throws RecordNotFoundException Se o posto de saúde não for encontrado
     */
    public HealthCenterResponseDTO updateHealthCenter(@NotNull @Positive Long id, @Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(healthCenterDTO.name());
                    recordFound.setAddress(healthCenterDTO.address());
                    recordFound.setOpeningHour(healthCenterMapper.parseTime(healthCenterDTO.openingHour()));
                    recordFound.setClosingHour(healthCenterMapper.parseTime(healthCenterDTO.closingHour()));

                    // Limpa especialidades antigas e adiciona as novas
                    recordFound.getSpecialties().clear();
                    healthCenterDTO.specialtyIds().forEach(specialtyId -> {
                        Specialty specialty = specialtyRepository.findById(specialtyId)
                                .orElseThrow(() -> new RecordNotFoundException("Especialidade não encontrada com o id: " + specialtyId));
                        recordFound.getSpecialties().add(specialty);
                    });

                    return healthCenterMapper.toDTO(healthCenterRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    /**
     * Adiciona médicos a um posto de saúde.
     * @param id ID do posto de saúde
     * @param healthCenterDTO Dados do posto de saúde com médicos
     * @return DTO do posto de saúde atualizado
     * @throws RecordNotFoundException Se o posto de saúde ou médico não for encontrado
     */
    public HealthCenterResponseDTO addDoctors(@NotNull @Positive Long id, @Valid @NotNull HealthCenterDTO healthCenterDTO) {
        return healthCenterRepository.findById(id)
                .map(recordFound -> {
                    // Remove médicos antigos e limpa a lista
                    recordFound.getDoctors().forEach(doctor -> doctor.getHealthCenters().remove(recordFound));
                    doctorRepository.saveAll(recordFound.getDoctors());
                    recordFound.getDoctors().clear();

                    // Adiciona médicos novos ao posto de saúde
                    healthCenterDTO.doctorIds().forEach(doctorId -> {
                        Doctor doctor = doctorRepository.findById(doctorId)
                                .orElseThrow(() -> new RecordNotFoundException("Médico não encontrado com o id: " + doctorId));

                        doctor.getHealthCenters().add(recordFound);
                        recordFound.getDoctors().add(doctor);
                    });

                    doctorRepository.saveAll(recordFound.getDoctors());
                    return healthCenterMapper.toDTO(healthCenterRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException("Posto de saúde não encontrado com o id: " + id));
    }

    /**
     * Exclui um posto de saúde pelo ID.
     * @param id ID do posto de saúde
     * @throws RecordNotFoundException Se o posto de saúde não for encontrado
     */
    public void deleteHealthCenter(@NotNull @Positive Long id) {
        healthCenterRepository.delete(healthCenterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
