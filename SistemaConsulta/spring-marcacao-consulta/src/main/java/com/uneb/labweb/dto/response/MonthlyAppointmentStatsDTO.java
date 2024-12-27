package com.uneb.labweb.dto.response;

public record MonthlyAppointmentStatsDTO(
        /**
         * Mês referente às estatísticas de consultas.
         * Representa o mês do ano (1 a 12).
         */
        int month,

        /**
         * Número de consultas agendadas no mês.
         * Refere-se ao total de consultas marcadas para o mês.
         */
        int scheduledCount,

        /**
         * Número de consultas atendidas no mês.
         * Refere-se ao total de consultas realizadas com a presença do paciente.
         */
        int attendedCount,

        /**
         * Número de consultas não comparecidas no mês.
         * Refere-se ao total de consultas em que o paciente não compareceu.
         */
        int missedCount,

        /**
         * Número de consultas canceladas no mês.
         * Refere-se ao total de consultas que foram canceladas.
         */
        int cancelledCount
) {

}
