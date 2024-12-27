package com.uneb.labweb.dto.request;

import jakarta.validation.constraints.Pattern;

public record PenaltyDTO(
        Long id,

        /**
         * Data de início da penalidade. O formato esperado é "dd/MM/yyyy" (exemplo: 01/01/2024).
         */
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
        String penaltyStartDate,

        /**
         * Data de término da penalidade. O formato esperado é "dd/MM/yyyy" (exemplo: 01/01/2024).
         */
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$") 
        String penaltyEndDate

        // @NotNull
        // Long userId
) {

}

