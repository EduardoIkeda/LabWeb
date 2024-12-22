package com.uneb.labweb.dto.response;

import org.hibernate.validator.constraints.Length;

import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.enums.validation.ValueOfEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AppointmentResponseDTO(
        Long id,

        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$") // Ex: dd/MM/yyyy HH:mm
        @NotBlank
        @Length(min = 16, max = 16)
        String appointmentDateTime,
        
        @NotBlank
        @Length(min = 6, max = 10)
        @ValueOfEnum(enumClass = AppointmentStatus.class)
        String appointmentStatus,

        @NotBlank
        String citizenName,

        @NotBlank
        String doctorName,

        @NotBlank
        String specialtyName,

        @NotBlank
        String healthCenterName
) {
    
}
