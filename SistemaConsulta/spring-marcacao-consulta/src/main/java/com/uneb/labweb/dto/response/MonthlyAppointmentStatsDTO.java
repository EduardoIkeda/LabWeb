package com.uneb.labweb.dto.response;

public record MonthlyAppointmentStatsDTO(
        int month,
        int scheduledCount,
        int attendedCount,
        int missedCount,
        int cancelledCount
) {

}
