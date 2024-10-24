package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Validações ainda genéricas
public record EspecialidadeDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) String nome
) {
    
}
