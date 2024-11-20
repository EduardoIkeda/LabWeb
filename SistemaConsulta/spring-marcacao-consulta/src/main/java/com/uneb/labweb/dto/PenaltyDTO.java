package com.uneb.labweb.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Validações ainda genéricas
public record PenaltyDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) LocalDateTime dataInicioPenalizacao,
        @NotBlank @NotNull @Length(min = 5, max = 100) LocalDateTime dataFimPenalizacao
) {
    
}
