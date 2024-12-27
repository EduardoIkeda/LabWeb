package com.uneb.labweb.dto.response;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record DoctorResponseDTO(
        /**
         * Identificador único do médico.
         */
        Long id,

        /**
         * Nome do médico.
         * Este campo é obrigatório e não pode ser vazio.
         */
        @NotBlank
        String doctorName,

        /**
         * Número do CRM (Conselho Regional de Medicina) do médico, no formato "12345-BA".
         * A regex especifica o formato do número do CRM com o código do estado.
         */
        @Pattern(regexp = "^[1-9]\\d{0,5}-(A[CLPM]|BA|CE|DF|ES|GO|M[ATSG]|P[ABREI]|R[JNSOR]|S[CPE]|TO)$")
        String crm,

        /**
         * Hora de início do expediente do médico. No formato "HH:mm" (ex: 08:00).
         * Este campo representa o horário de início do trabalho diário do médico.
         */
        @Pattern(regexp = "^\\d{2}:\\d{2}$")
        String startWork,

        /**
         * Hora de término do expediente do médico. No formato "HH:mm" (ex: 18:00).
         * Este campo representa o horário de término do trabalho diário do médico.
         */
        @Pattern(regexp = "^\\d{2}:\\d{2}$")
        String endWork,

        /**
         * Dias da semana em que o médico trabalha. Este campo é obrigatório e não pode ser vazio.
         * Cada dia de trabalho deve ser representado por uma string (ex: "Segunda", "Terça").
         */
        @NotEmpty
        List<String> workingDays,

        /**
         * Lista de consultas agendadas para este médico.
         * Cada consulta é representada por um objeto de `AppointmentResponseDTO`.
         */
        List<AppointmentResponseDTO> doctorAppointments
) {

}
