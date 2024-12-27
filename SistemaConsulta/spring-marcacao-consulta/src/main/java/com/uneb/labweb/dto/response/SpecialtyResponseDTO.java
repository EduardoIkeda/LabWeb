package com.uneb.labweb.dto.response;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record SpecialtyResponseDTO(
        /**
         * ID único da especialidade médica. Representa o identificador único
         * para a especialidade no sistema.
         */
        Long id,

        /**
         * Nome da especialidade médica. Nome da especialidade, como
         * "Cardiologia", "Pediatria", etc. A especialidade deve ter entre 5 e
         * 100 caracteres.
         */
        @NotBlank
        @Length(min = 5, max = 100)
        String name
) {

}
