package com.uneb.labweb.dto.request;

import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.enums.validation.ValueOfEnum;

import jakarta.validation.constraints.Pattern;

public record AppointmentDTO(
        Long id,

        /**
         * Data e hora do agendamento da consulta.
         * O formato da data e hora deve ser "dd/MM/yyyy HH:mm".
         * Exemplo: "25/12/2024 14:30".
         */
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$")
        String appointmentDateTime,

        /**
         * Status do agendamento da consulta.
         * O valor deve ser um dos valores válidos do enum {@link AppointmentStatus}.
         */
        @ValueOfEnum(enumClass = AppointmentStatus.class)
        String appointmentStatus,

        /**
         * ID do médico responsável pela consulta.
         */
        Long doctorId,

        /**
         * ID do posto de saúde onde a consulta será realizada.
         */
        Long healthCenterId,

        /**
         * ID da especialidade para a consulta.
         */
        Long specialtyId,

        /**
         * ID do paciente agendado para a consulta.
         */
        Long patientId
) {

}
