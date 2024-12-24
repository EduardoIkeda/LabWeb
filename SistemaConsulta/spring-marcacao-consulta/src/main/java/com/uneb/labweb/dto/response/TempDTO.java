package com.uneb.labweb.dto.response;

public record TempDTO(
    Long patientId,
    String doctorName,
    String specialtyName,
    String healthCenterName,
    String healthCenterAddress
) {
    
}
