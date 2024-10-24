package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Validações ainda genéricas
public record PostoSaudeDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) String nome,
        @NotBlank @NotNull @Length(min = 5, max = 100) String endereco,
        @NotBlank @NotNull @Length(min = 5, max = 100) String especialidades,
        @NotBlank @NotNull @Length(min = 5, max = 100) String horarioFuncionamento
) {

}
