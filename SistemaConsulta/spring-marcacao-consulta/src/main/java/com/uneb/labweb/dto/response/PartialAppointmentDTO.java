package com.uneb.labweb.dto.response;

public record PartialAppointmentDTO(
        /**
         * ID do paciente associado à consulta. Representa o identificador único
         * do paciente para a consulta.
         */
        Long patientId,

        /**
         * ID do posto de saúde onde a consulta será realizada. Representa o
         * identificador único do posto de saúde para a consulta.
         */
        Long healthCenterId,

        /**
         * ID da especialidade associada à consulta. Representa o identificador
         * único da especialidade para a consulta.
         */
        Long specialtyId,

        /**
         * Nome do médico que irá realizar a consulta. Representa o nome do
         * médico responsável pela consulta.
         */
        String doctorName,

        /**
         * Nome da especialidade da consulta. Representa o nome da especialidade
         * médica relacionada à consulta.
         */
        String specialtyName,

        /**
         * Nome do posto de saúde onde a consulta será realizada. Representa o
         * nome do posto de saúde onde a consulta ocorrerá.
         */
        String healthCenterName,

        /**
         * Endereço do posto de saúde onde a consulta será realizada.
         * Representa o endereço completo do posto de saúde onde a consulta
         * ocorrerá.
         */
        String healthCenterAddress
) {

}
