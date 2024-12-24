package com.uneb.labweb.dto.response;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record HealthCenterResponseDTO(
        Long id,

        @NotBlank
        @Length(min = 5, max = 100)
        String name,

        @NotBlank
        @Length(min = 5, max = 100)
        String address,
        
        @NotBlank
        @Pattern(regexp = "^\\d{2}:\\d{2}$") // Ex: 08:00
        String openingHour,

        @NotBlank
        @Pattern(regexp = "^\\d{2}:\\d{2}$") // Ex: 18:00
        String closingHour,

        @NotNull
        List<SpecialtyResponseDTO> specialties,

        @NotNull
        long availableAppointmentsCount
) {
    
}
