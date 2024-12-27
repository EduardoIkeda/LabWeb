package com.uneb.labweb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.mapper.SpecialtyMapper;
import com.uneb.labweb.dto.request.SpecialtyDTO;
import com.uneb.labweb.dto.response.SpecialtyCountDTO;
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

    /**
     * Retorna todas as especialidades cadastradas.
     * @return Lista de DTOs de especialidades
     */
    public List<SpecialtyDTO> findAllSpecialties() {
        return specialtyRepository.findAll()
                .stream()
                .map(specialtyMapper::toDTO)
                .toList();
    }

    /**
     * Retorna uma especialidade pelo ID.
     * @param id ID da especialidade
     * @return DTO da especialidade
     * @throws RecordNotFoundException Se a especialidade não for encontrada
     */
    public SpecialtyDTO findSpecialtyById(@NotNull @Positive Long id) {
        return specialtyRepository.findById(id)
                .map(specialtyMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    /**
     * Retorna a contagem de atendimentos por especialidade em um ano específico.
     * @param year Ano para o qual será feita a contagem
     * @return Lista de DTOs com especialidades e suas respectivas contagens de atendimentos
     */
    public List<SpecialtyCountDTO> getSpecialtyAppointmentsCountByYear(@NotNull @Positive Long year) {
        return specialtyRepository.findSpecialtiesWithAppointmentsCountByYear(year);
    }

    /**
     * Cria uma nova especialidade.
     * @param specialtyDTO Dados da nova especialidade
     * @return DTO da especialidade criada
     */
    public SpecialtyDTO createSpecialty(@Valid @NotNull SpecialtyDTO specialtyDTO) {
        return specialtyMapper.toDTO(specialtyRepository.save(specialtyMapper.toEntity(specialtyDTO)));
    }

    /**
     * Atualiza uma especialidade existente.
     * @param id ID da especialidade
     * @param specialtyDTO Dados atualizados da especialidade
     * @return DTO da especialidade atualizada
     * @throws RecordNotFoundException Se a especialidade não for encontrada
     */
    public SpecialtyDTO updateSpecialty(@NotNull @Positive Long id, @Valid @NotNull SpecialtyDTO specialtyDTO) {
        return specialtyRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(specialtyDTO.name());

                    // Atualizações adicionais podem ser feitas aqui, conforme necessário
                    return specialtyMapper.toDTO(specialtyRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    /**
     * Exclui uma especialidade pelo ID.
     * @param id ID da especialidade
     * @throws RecordNotFoundException Se a especialidade não for encontrada
     */
    public void deleteSpecialty(@NotNull @Positive Long id) {
        specialtyRepository.delete(specialtyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
