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

    /**
     * Retorna todos os médicos cadastrados.
     * @return Lista de DTOs de médicos
     */
    public List<DoctorResponseDTO> findAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    /**
     * Retorna médicos de um posto de saúde específico.
     * @param healthCenterId ID do posto de saúde
     * @return Lista de médicos
     * @throws RecordNotFoundException Se o posto de saúde não for encontrado
     */
    public List<DoctorResponseDTO> findDoctorsByHealthCenter(@NotNull @Positive Long healthCenterId) {
        return healthCenterRepository.findById(healthCenterId)
                .map(recordFound -> doctorRepository.findByHealthCenterId(healthCenterId)
                        .stream()
                        .map(doctorMapper::toDTO)
                        .toList())
                .orElseThrow(() -> new RecordNotFoundException("Posto de saúde não encontrado com o id: " + healthCenterId));
    }


    /**
     * Busca o ID do médico associado a um determinado usuário.
     *
     * @param usuarioId o ID do usuário
     * @return o ID do médico, se encontrado
     * @throws RecordNotFoundException se nenhum médico estiver associado ao usuário
     */
    public Long findDoctorIdByUserId(Long userId) {
        Long doctorId = doctorRepository.findDoctorIdByUserId(userId);

        if (doctorId == null) {
            throw new RecordNotFoundException("Id de médico não encontrado com o id de usuário: " + userId);
        }
               
        return doctorId;
    }

    /**
     * Retorna um médico pelo ID.
     * @param id ID do médico
     * @return DTO do médico
     * @throws RecordNotFoundException Se o médico não for encontrado
     */
    public DoctorResponseDTO findDoctorById(@NotNull @Positive Long id) { 
        return doctorRepository.findById(id)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    /**
     * Cria um novo médico.
     * @param doctorDTO Dados do novo médico
     * @return DTO do médico criado
     */
    public DoctorResponseDTO createDoctor(@Valid @NotNull DoctorDTO doctorDTO) {
        return doctorMapper.toDTO(doctorRepository.save(doctorMapper.toEntity(doctorDTO)));
    }

    /**
     * Atualiza um médico existente.
     * @param id ID do médico
     * @param doctorDTO Dados atualizados do médico
     * @return DTO do médico atualizado
     * @throws RecordNotFoundException Se o médico não for encontrado
     */
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

    /**
     * Exclui um médico pelo ID.
     * @param id ID do médico a ser excluído
     * @throws RecordNotFoundException Se o médico não for encontrado
     */
    public void deleteDoctor(@NotNull @Positive Long id) {
        doctorRepository.delete(doctorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
