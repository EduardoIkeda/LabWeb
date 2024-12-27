package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        /**
         * Número do cartão SUS do usuário. Deve ser uma string não vazia com no mínimo 5 caracteres e no máximo 100.
         */
        @NotBlank
        @Length(min = 5, max = 100)
        String susCardNumber,
        
        /**
         * Senha do usuário. Deve ser uma string não vazia com no mínimo 5 caracteres e no máximo 100.
         */
        @NotBlank
        @Length(min = 5, max = 100)
        String password
) {

}
