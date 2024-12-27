package com.uneb.labweb.dto.response;

import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.enums.validation.ValueOfEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AppointmentResponseDTO(
        /**
         * Identificador único do agendamento.
         */
        Long id,

        /**
         * Data e hora do agendamento, no formato "dd/MM/yyyy HH:mm".
         * Exemplo: "25/12/2024 14:30"
         */
        @NotBlank
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$")
        String appointmentDateTime,
        
        /**
         * Status do agendamento. Deve ser um valor válido da enum AppointmentStatus.
         */
        @NotBlank
        @ValueOfEnum(enumClass = AppointmentStatus.class)
        String appointmentStatus,

        /**
         * Identificador do paciente relacionado ao agendamento.
         */
        Long patientId,

        /**
         * Identificador do posto de saúde onde o agendamento ocorreu.
         */
        @NotNull
        Long healthCenterId,

        /**
         * Identificador da especialidade associada ao agendamento.
         */
        @NotNull
        Long specialtyId,

        /**
         * Nome do médico associado ao agendamento.
         */
        @NotBlank
        String doctorName,

        /**
         * Nome da especialidade associada ao agendamento.
         */
        @NotBlank
        String specialtyName,

        /**
         * Nome do posto de saúde associado ao agendamento.
         */
        @NotBlank
        String healthCenterName,

        /**
         * Endereço do posto de saúde associado ao agendamento.
         */
        @NotBlank
        String healthCenterAddress,

        /**
         * Indica se o agendamento é para amanhã (true) ou não (false).
         */
        boolean isTomorrow,

        /**
         * Indica se o agendamento foi finalizado (true) ou não (false).
         */
        boolean isFinalized
) {
    
}
