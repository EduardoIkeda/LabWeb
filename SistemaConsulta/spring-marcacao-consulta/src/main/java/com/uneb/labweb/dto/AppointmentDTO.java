package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AppointmentDTO(
        Long id,

        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$") // Ex: dd/MM/yyyy HH:mm
        @NotBlank
        @Length(min = 16, max = 16)
        String appointmentDateTime,
        
        Boolean attended
) {

}
