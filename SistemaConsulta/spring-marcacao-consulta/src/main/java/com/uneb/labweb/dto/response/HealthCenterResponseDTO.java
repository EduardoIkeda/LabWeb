package com.uneb.labweb.dto.response;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record HealthCenterResponseDTO(
        /**
         * Identificador único do posto de saúde.
         */
        Long id,

        /**
         * Nome do posto de saúde.
         * Este campo é obrigatório e deve ter entre 5 e 100 caracteres.
         */
        @NotBlank
        @Length(min = 5, max = 100)
        String name,

        /**
         * Endereço do posto de saúde.
         * Este campo é obrigatório e deve ter entre 5 e 100 caracteres.
         */
        @NotBlank
        @Length(min = 5, max = 100)
        String address,

        /**
         * Hora de abertura do posto de saúde. 
         * No formato "HH:mm" (ex: 08:00).
         */
        @NotBlank
        @Pattern(regexp = "^\\d{2}:\\d{2}$")
        String openingHour,

        /**
         * Hora de fechamento do posto de saúde. 
         * No formato "HH:mm" (ex: 18:00).
         */
        @NotBlank
        @Pattern(regexp = "^\\d{2}:\\d{2}$")
        String closingHour,

        /**
         * Lista de especialidades disponíveis no posto de saúde.
         * Cada especialidade é representada por um objeto do tipo `SpecialtyResponseDTO`.
         */
        @NotNull
        List<SpecialtyResponseDTO> specialties,

        /**
         * Contagem de consultas disponíveis no posto de saúde.
         * Este campo é obrigatório e deve ser um número não nulo.
         */
        @NotNull
        long availableAppointmentsCount
) {

}
