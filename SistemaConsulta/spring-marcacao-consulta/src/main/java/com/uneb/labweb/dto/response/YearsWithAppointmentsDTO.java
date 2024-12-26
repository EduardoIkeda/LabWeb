package com.uneb.labweb.dto.response;

import java.util.List;

public record YearsWithAppointmentsDTO(
        int year,
        List<MonthlyAppointmentStatsDTO> monthlyStats
        //List<Integer> months
) {

}
