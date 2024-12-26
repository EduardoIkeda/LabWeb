package com.uneb.labweb.dto.response;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record DoctorResponseDTO(
        Long id,
    
        @NotBlank
        String doctorName,

        @NotBlank
        @Pattern(regexp = "^[1-9]\\d{0,5}-(A[CLPM]|BA|CE|DF|ES|GO|M[ATSG]|P[ABREI]|R[JNSOR]|S[CPE]|TO)$") // Ex: 12345-BA
        String crm,
    
        @NotBlank
        @Pattern(regexp = "^\\d{2}:\\d{2}$") // Ex: 08:00
        String startWork,
    
        @NotBlank
        @Pattern(regexp = "^\\d{2}:\\d{2}$") // Ex: 18:00
        String endWork,
    
        @NotEmpty
        List<String> workingDays,

        List<AppointmentResponseDTO> doctorAppointments
) {
    
}
