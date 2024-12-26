package com.uneb.labweb.dto.response;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record SpecialtyResponseDTO(
        Long id,

        @NotBlank
        @Length(min = 5, max = 100) 
        String name
) {
    
}
