package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.SpecialtyDTO;
import com.uneb.labweb.model.Specialty;

@Component
public class SpecialtyMapper {

    public SpecialtyDTO toDTO(Specialty specialty) {
        if (specialty == null) {
            return null;
        }

        return new SpecialtyDTO(specialty.getId(), specialty.getName());
    }
    
    //public Specialty toEntity(SpecialtyDTO specialtyDTO) { }
}
