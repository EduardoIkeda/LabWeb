package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDTO(
    @NotBlank @NotNull @Length(min = 5, max = 100) String susCardNumber,
    @NotBlank @NotNull @Length(min = 5, max = 100) String password
) {

}
