package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Validações ainda genéricas
public record UsuarioDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) String numeroCartaoSus,
        @NotBlank @NotNull @Length(min = 5, max = 100) String nome,
        @NotBlank @NotNull @Length(min = 5, max = 100) String cpf,
        @NotBlank @NotNull @Length(min = 5, max = 100) String telefone,
        @NotBlank @NotNull @Length(min = 5, max = 100) String email,
        @NotBlank @NotNull @Length(min = 5, max = 100) String senha
) {
    
}
