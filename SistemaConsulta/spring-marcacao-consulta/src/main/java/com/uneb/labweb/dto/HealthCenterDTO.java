package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record HealthCenterDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) String name,
        @NotBlank @NotNull @Length(min = 5, max = 100) String address,
        @Pattern(regexp = "^\\d{2}:\\d{2}[\\-–—]?\\d{2}:\\d{2}$")
        @NotBlank @NotNull @Length(min = 5, max = 100) String operatingHours,
        @NotBlank @NotNull @Length(min = 5, max = 100) String specialties
) {

}
