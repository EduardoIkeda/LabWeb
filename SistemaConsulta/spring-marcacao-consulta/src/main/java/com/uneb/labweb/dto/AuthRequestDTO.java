package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank
        @Length(min = 5, max = 100)
        String susCardNumber,
        
        @NotBlank
        @Length(min = 5, max = 100)
        String password
) {

}
