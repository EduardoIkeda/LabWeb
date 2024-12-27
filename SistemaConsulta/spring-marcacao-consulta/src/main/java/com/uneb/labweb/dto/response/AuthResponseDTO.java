package com.uneb.labweb.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthResponseDTO(
        /**
         * Identificador único do usuário autenticado.
         */
        @NotNull
        Long id,

        /**
         * Nome do usuário autenticado.
         */
        @NotBlank
        String name,

        /**
         * URL do avatar do usuário.
         */
        @NotBlank
        String avatarUrl,

        /**
         * Função ou cargo do usuário (ex: admin, doctor, citizen).
         */
        @NotBlank
        String role,

        /**
         * Token JWT gerado após a autenticação bem-sucedida.
         * Esse token será utilizado para autenticação em requisições subsequentes.
         */
        @NotBlank
        String token
) {

}
