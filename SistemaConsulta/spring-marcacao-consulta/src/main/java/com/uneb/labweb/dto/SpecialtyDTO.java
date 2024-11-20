package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Validações ainda genéricas
public record SpecialtyDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) String name
) {
    
}
