package com.uneb.labweb.dto.request;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record HealthCenterDTO(
        Long id,

        /**
         * Nome do posto de saúde. Deve ser uma string não vazia com no mínimo 5 caracteres e no máximo 100.
         */
        @NotBlank
        @Length(min = 5, max = 100)
        String name,

        /**
         * Endereço do posto de saúde. Deve ser uma string não vazia com no mínimo 5 caracteres e no máximo 100.
         */
        @NotBlank
        @Length(min = 5, max = 100)
        String address,
        
        /**
         * Hora de abertura do posto de saúde no formato "HH:mm".
         * Exemplo: "08:00".
         */
        @Pattern(regexp = "^\\d{2}:\\d{2}$")
        String openingHour,

        /**
         * Hora de fechamento do posto de saúde no formato "HH:mm".
         * Exemplo: "18:00".
         */
        @Pattern(regexp = "^\\d{2}:\\d{2}$")
        String closingHour,

        /**
         * Lista de IDs de especialidades oferecidas pelo posto de saúde. Não pode ser vazia.
         */
        @NotEmpty
        List<Long> specialtyIds,

        /**
         * Lista de IDs dos médicos associados ao posto de saúde. Este campo é opcional.
         */
        List<Long> doctorIds
) {

}
