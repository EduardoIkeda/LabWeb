package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record SpecialtyDTO(
        Long id,

        @NotBlank
        @Length(min = 5, max = 100) 
        String name//,

        // @NotEmpty
        // List<Long> doctorIds,

        // @NotEmpty
        // List<Long> healthCenterIds
) {
    
}
