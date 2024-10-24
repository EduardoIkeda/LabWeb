package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.EspecialidadeDTO;
import com.uneb.labweb.model.Especialidade;

@Component
public class EspecialidadeMapper {

    public EspecialidadeDTO toDTO(Especialidade esp) {
        if (esp == null) {
            return null;
        }

        return new EspecialidadeDTO(esp.getId(), esp.getNome());
    }
    
    //public Especialidade toEntity(EspecialidadeDTO espDTO) { }
}
