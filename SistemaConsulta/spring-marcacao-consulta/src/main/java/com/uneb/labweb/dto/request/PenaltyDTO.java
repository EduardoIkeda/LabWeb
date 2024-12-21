package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PenaltyDTO(
        Long id,

        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$") // Ex: dd/MM/yyyy
        @NotBlank
        @Length(min = 10, max = 10)
        String penaltyStartDate,

        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$") // Ex: dd/MM/yyyy
        @NotBlank
        @Length(min = 10, max = 10)
        String penaltyEndDate//,

        // @NotNull
        // Long userId
) {

}
