package com.uneb.labweb.dto.response;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record SpecialtyResponseDTO(
        Long id,

        @NotBlank
        @Length(min = 5, max = 100) 
        String name,

        @NotEmpty
        List<HealthCenterResponseDTO> healthCenters,

        @NotEmpty
        List<AppointmentResponseDTO> specialtyAppointments
) {
    
}
