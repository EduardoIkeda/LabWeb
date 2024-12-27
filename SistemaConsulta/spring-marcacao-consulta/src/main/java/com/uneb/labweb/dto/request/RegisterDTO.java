package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(
        Long id,

        /**
         * Número do Cartão do SUS. Deve ter exatamente 15 dígitos (exemplo: 012345678901234).
         */
        @Pattern(regexp = "^\\d{15}$")
        @Length(min = 15, max = 15) 
        String susCardNumber,

        /**
         * Nome completo do usuário. Deve ter entre 5 e 100 caracteres.
         */
        @NotBlank 
        @Length(min = 5, max = 100) 
        String name,

        /**
         * CPF do usuário. Deve ter exatamente 11 dígitos (exemplo: 01234567890).
         */
        @Pattern(regexp = "^\\d{11}$")
        @Length(min = 11, max = 11) 
        String cpf,

        /**
         * Número de telefone do usuário. Pode ter entre 10 e 12 dígitos (exemplos: 71982345678, 7136485678).
         */
        @Pattern(regexp = "^\\d{10,12}$")
        String phone,

        /**
         * E-mail do usuário. Deve ser um endereço de e-mail válido e ter entre 6 e 100 caracteres.
         */
        @Email 
        @NotBlank 
        @Length(min = 6, max = 100) 
        String email,

        /**
         * Senha do usuário. Deve ter entre 5 e 100 caracteres.
         */
        @NotBlank 
        @Length(min = 5, max = 100) 
        String password
) {

}
