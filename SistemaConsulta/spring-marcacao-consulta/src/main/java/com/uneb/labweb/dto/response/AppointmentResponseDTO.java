package com.uneb.labweb.dto.response;

import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.enums.validation.ValueOfEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AppointmentResponseDTO(
        Long id,

        @NotBlank
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$") // Ex: dd/MM/yyyy HH:mm
        String appointmentDateTime,
        
        @NotBlank
        @ValueOfEnum(enumClass = AppointmentStatus.class)
        String appointmentStatus,

        Long patientId,

        @NotBlank
        String doctorName,

        @NotBlank
        String specialtyName,

        @NotBlank
        String healthCenterName,

        @NotBlank
        String healthCenterAddress
) {
    
}
