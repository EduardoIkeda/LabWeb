package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 15, max = 15) String susCardNumber,
        @NotBlank @NotNull @Length(min = 5, max = 100) String name,
        @NotBlank @NotNull @Length(min = 11, max = 11) String cpf,
        @NotBlank @NotNull @Length(min = 9, max = 20) String phone,
        @Email @NotBlank @NotNull @Length(min = 6, max = 100) String email,
        @NotBlank @NotNull @Length(min = 5, max = 100) String password
) {
    
}
