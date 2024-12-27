package com.uneb.labweb.dto.response;

public record SpecialtyCountDTO(
        /**
         * Nome da especialidade médica. Representa o nome da especialidade para
         * a qual os dados de consultas estão sendo contabilizados.
         */
        String specialtyName,

        /**
         * Contagem total de consultas associadas à especialidade. Representa o
         * número total de consultas realizadas para a especialidade médica
         * específica.
         */
        Long appointmentsCount
) {

}
