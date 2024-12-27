package com.uneb.labweb.dto.response;

import java.util.List;

public record AppointmentsByDateDTO(
        /**
         * Data para a qual os agendamentos estão sendo retornados.
         * Exemplo: "25/12/2024"
         */
        String date,

        /**
         * Lista de médicos associados aos agendamentos para a data especificada.
         * Cada elemento na lista é um DTO que contém as informações do médico.
         */
        List<DoctorResponseDTO> doctors
) { 
        
}
