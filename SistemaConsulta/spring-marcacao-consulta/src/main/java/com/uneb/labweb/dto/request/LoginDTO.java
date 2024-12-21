package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank
        @Length(min = 5, max = 100)
        String susCardNumber,
        
        @NotBlank
        @Length(min = 5, max = 100)
        String password
) {

}
