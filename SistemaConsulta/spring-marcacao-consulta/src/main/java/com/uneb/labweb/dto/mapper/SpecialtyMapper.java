package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.SpecialtyDTO;
import com.uneb.labweb.model.Specialty;

@Component
public class SpecialtyMapper {

    /**
     * Converte uma entidade Specialty para um DTO de SpecialtyDTO.
     * @param specialty A entidade Specialty a ser convertida.
     * @return O SpecialtyDTO correspondente.
     */
    public SpecialtyDTO toDTO(Specialty specialty) {
        if (specialty == null) {
            return null;
        }

        // Cria e retorna o DTO com os dados da especialidade
        return new SpecialtyDTO(specialty.getId(), specialty.getName());
    }
    
    /**
     * Converte um DTO de SpecialtyDTO para a entidade Specialty.
     * @param specialtyDTO O DTO de Specialty a ser convertido.
     * @return A entidade Specialty correspondente.
     */
    public Specialty toEntity(SpecialtyDTO specialtyDTO) { 
        if (specialtyDTO == null) {
            return null;
        }

        Specialty specialty = new Specialty();

        if (specialtyDTO.id() != null) {
            specialty.setId(specialtyDTO.id());
        }
        specialty.setName(specialtyDTO.name());
        
        return specialty;
    }
}
