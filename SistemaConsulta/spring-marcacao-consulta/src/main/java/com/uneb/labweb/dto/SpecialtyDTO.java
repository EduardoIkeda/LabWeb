package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record SpecialtyDTO(
        Long id,

        @NotBlank
        @Length(min = 5, max = 100) 
        String name,

        @NotBlank 
        @Length(min = 5, max = 255)
        String description
) {
    
}
