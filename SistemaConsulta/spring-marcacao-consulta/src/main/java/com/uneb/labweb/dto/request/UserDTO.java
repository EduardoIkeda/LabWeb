package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import com.uneb.labweb.enums.Role;
import com.uneb.labweb.enums.UserStatus;
import com.uneb.labweb.enums.validation.ValueOfEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        Long id,

        /**
         * Número do Cartão SUS. Deve conter exatamente 15 dígitos.
         * Exemplo: 012345678901234
         */
        @Pattern(regexp = "^\\d{15}$")
        String susCardNumber,

        /**
         * Nome completo do usuário. Deve ter entre 5 e 100 caracteres.
         */
        @NotBlank
        @Length(min = 5, max = 100) 
        String name,

        /**
         * CPF do usuário. Deve conter exatamente 11 dígitos.
         * Exemplo: 01234567890
         */
        @Pattern(regexp = "^\\d{11}$")
        String cpf,

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
        @NotBlank 
        @Length(min = 6, max = 100) 
        String email,

        /**
         * Senha do usuário. Deve ter entre 5 e 100 caracteres.
         */
        @NotBlank 
        @Length(min = 5, max = 100) 
        String password,

        /**
         * URL do avatar do usuário. Deve ter entre 5 e 400 caracteres.
         */
        @Length(min = 5, max = 400)
        String avatarUrl,

        /**
         * Status do usuário. Deve corresponder a um valor definido no enum UserStatus.
         * O valor deve ter entre 3 e 10 caracteres.
         */
        @Length(min = 3, max = 10)
        @ValueOfEnum(enumClass = UserStatus.class)
        String status,

        /**
         * Função ou cargo do usuário. Deve corresponder a um valor definido no enum Role.
         * O valor deve ter entre 3 e 10 caracteres.
         */
        @Length(min = 3, max = 10)
        @ValueOfEnum(enumClass = Role.class)
        String role
) {
    
}