package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Validações ainda genéricas
public record UserDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) String susCardNumber,
        @NotBlank @NotNull @Length(min = 5, max = 100) String name,
        @NotBlank @NotNull @Length(min = 5, max = 100) String cpf,
        @NotBlank @NotNull @Length(min = 5, max = 100) String phone,
        @NotBlank @NotNull @Length(min = 5, max = 100) String email,
        @NotBlank @NotNull @Length(min = 5, max = 100) String password
) {
    
}
