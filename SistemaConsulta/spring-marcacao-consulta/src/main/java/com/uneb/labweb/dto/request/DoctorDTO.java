package com.uneb.labweb.dto.request;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record DoctorDTO(
        Long id,

        /**
         * CRM do médico. Deve estar no formato:
         * "12345-BA" ou "12345-ES", por exemplo.
         * A primeira parte é um número de até 6 dígitos, seguido por um traço e a sigla do estado.
         * A sigla do estado deve ser uma das especificadas no padrão.
         */
        @Pattern(regexp = "^[1-9]\\d{0,5}-(A[CLPM]|BA|CE|DF|ES|GO|M[ATSG]|P[ABREI]|R[JNSOR]|S[CPE]|TO)$")
        @Length(min = 4, max = 9)
        String crm,

        /**
         * Hora de início do expediente do médico no formato "HH:mm".
         * Exemplo: "08:00".
         */
        @Pattern(regexp = "^\\d{2}:\\d{2}$")
        String startWork,

        /**
         * Hora de término do expediente do médico no formato "HH:mm".
         * Exemplo: "18:00".
         */
        @Pattern(regexp = "^\\d{2}:\\d{2}$")
        String endWork,

        /**
         * Lista dos dias da semana em que o médico trabalha.
         * Não pode ser vazio.
         */
        @NotEmpty
        List<String> workingDays,

        /**
         * ID do usuário associado ao médico.
         */
        Long userId,

        /**
         * Lista de IDs dos postos de saúde nos quais o médico atende.
         */
        List<Long> healthCenterIds,

        /**
         * Lista de IDs das especialidades que o médico oferece.
         */
        List<Long> specialtyIds
) {

}
