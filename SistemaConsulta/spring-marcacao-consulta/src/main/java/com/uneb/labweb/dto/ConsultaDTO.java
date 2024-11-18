package com.uneb.labweb.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Validações ainda genéricas
public record ConsultaDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) Long userId,
        @NotBlank @NotNull @Length(min = 5, max = 100) Long postoSaudeId,
        @NotBlank @NotNull @Length(min = 5, max = 100) Long especialidadeId,
        @NotBlank @NotNull @Length(min = 5, max = 100) LocalDateTime dataConsulta,
        @NotBlank @NotNull @Length(min = 5, max = 100) boolean compareceu
) {
    
}
