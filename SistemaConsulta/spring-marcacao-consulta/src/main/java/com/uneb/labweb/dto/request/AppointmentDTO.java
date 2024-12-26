package com.uneb.labweb.dto.request;

import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.enums.validation.ValueOfEnum;

import jakarta.validation.constraints.Pattern;

public record AppointmentDTO(
        Long id,

        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$") // Ex: dd/MM/yyyy HH:mm
        String appointmentDateTime,
        
        @ValueOfEnum(enumClass = AppointmentStatus.class)
        String appointmentStatus,

        Long doctorId,

        Long healthCenterId,

        Long specialtyId,

        Long patientId
) {

}
