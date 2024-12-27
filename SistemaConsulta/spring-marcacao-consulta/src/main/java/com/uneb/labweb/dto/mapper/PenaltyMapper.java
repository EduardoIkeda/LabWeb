package com.uneb.labweb.dto.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.PenaltyDTO;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.model.Penalty;

@Component
public class PenaltyMapper {

    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Converte uma entidade Penalty para um DTO de PenaltyDTO.
     * @param penalty A entidade Penalty a ser convertida.
     * @return O PenaltyDTO correspondente.
     */
    public PenaltyDTO toDTO(Penalty penalty) {
        if (penalty == null) {
            return null;
        }

        // Formata as datas de início e fim da penalidade
        String startDate = penalty.getPenaltyStartDate().format(FORMATTER_BR);
        String endDate = penalty.getPenaltyEndDate().format(FORMATTER_BR);

        // Retorna o DTO com os dados da penalidade
        return new PenaltyDTO(penalty.getId(), startDate, endDate);
    }

    /**
     * Converte um DTO de PenaltyDTO para a entidade Penalty.
     * @param penaltyDTO O DTO de Penalty a ser convertido.
     * @return A entidade Penalty correspondente.
     */
    public Penalty toEntity(PenaltyDTO penaltyDTO) {
        if (penaltyDTO == null) {
            return null;
        }

        Penalty penalty = new Penalty();

        if (penaltyDTO.id() != null) {
            penalty.setId(penaltyDTO.id());
        }
        penalty.setPenaltyStartDate(parseDate(penaltyDTO.penaltyStartDate()));
        penalty.setPenaltyEndDate(parseDate(penaltyDTO.penaltyEndDate()));

        return penalty;
    }

    /**
     * Converte uma string de data no formato "dd/MM/yyyy" para LocalDate.
     * @param dateString A string de data a ser convertida.
     * @return O LocalDate correspondente.
     * @throws InvalidDateTimeException Se a conversão falhar.
     */
    public LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, FORMATTER_BR);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException(dateString, e);
        }
    }
}
