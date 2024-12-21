package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.enums.validation.ValueOfEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AppointmentDTO(
        Long id,

        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$") // Ex: dd/MM/yyyy HH:mm
        @NotBlank
        @Length(min = 16, max = 16)
        String appointmentDateTime,
        
        @NotBlank
        @Length(min = 6, max = 10)
        @ValueOfEnum(enumClass = AppointmentStatus.class)
        String appointmentStatus//,

        // @NotNull
        // Long doctorId,

        // @NotNull
        // Long healthCenterId,

        // @NotNull
        // Long specialtyId,

        // @NotNull
        // Long userId
) {

}
