package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record HealthCenterDTO(
        Long id,

        @NotBlank
        @Length(min = 5, max = 100)
        String name,

        @NotBlank
        @Length(min = 5, max = 100)
        String address,
        
        @Pattern(regexp = "^\\d{2}:\\d{2}$") // Ex: 08:00
        @NotBlank
        @Length(min = 5, max = 5)
        String openingHour,

        @Pattern(regexp = "^\\d{2}:\\d{2}$") // Ex: 18:00
        @NotBlank
        @Length(min = 5, max = 5)
        String closingHour
) {

}
