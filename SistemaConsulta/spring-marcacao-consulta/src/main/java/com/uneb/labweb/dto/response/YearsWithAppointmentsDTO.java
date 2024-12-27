package com.uneb.labweb.dto.response;

import java.util.List;

public record YearsWithAppointmentsDTO(
        /**
         * Ano relacionado aos dados de consulta.
         * Representa o ano em que as consultas foram realizadas.
         */
        int year,

        /**
         * Lista de estatísticas mensais de consultas para o ano especificado.
         * Cada objeto da lista representa as estatísticas de um mês específico.
         */
        List<MonthlyAppointmentStatsDTO> monthlyStats
) {

}
