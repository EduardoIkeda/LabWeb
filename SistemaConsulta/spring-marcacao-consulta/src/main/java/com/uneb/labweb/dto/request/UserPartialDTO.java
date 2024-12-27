package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserPartialDTO(
        /**
         * URL do avatar do usuário. Deve ter entre 5 e 400 caracteres.
         */
        @Length(min = 5, max = 400)
        String avatarUrl,

        /**
         * Número de telefone do usuário. Pode ter entre 10 e 12 dígitos.
         * Exemplos: 71982345678, 7136485678
         */
        @Pattern(regexp = "^\\d{10,12}$")
        String phone,

        /**
         * Endereço de e-mail do usuário. Deve ser válido e ter entre 6 e 100 caracteres.
         */
        @Email  
        @Length(min = 6, max = 100) 
        String email,

        /**
         * Senha do usuário. Deve ter entre 5 e 100 caracteres.
         */
        @Length(min = 5, max = 100) 
        String password
) {
        
}
