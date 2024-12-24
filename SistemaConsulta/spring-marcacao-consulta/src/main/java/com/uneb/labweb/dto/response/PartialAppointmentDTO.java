package com.uneb.labweb.dto.response;

public record PartialAppointmentDTO(
    Long patientId,
    Long healthCenterId,
    Long specialtyId,
    String doctorName,
    String specialtyName,
    String healthCenterName,
    String healthCenterAddress
) {
    
}
