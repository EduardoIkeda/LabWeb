package com.uneb.labweb.dto.response;

import java.util.List;

public record AppointmentsByDateDTO(
        String date,
        List<DoctorResponseDTO> doctors
) { 
        
}
