package com.uneb.labweb.dto.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.PenaltyDTO;
import com.uneb.labweb.exception.InvalidDateTimeException;
import com.uneb.labweb.model.Penalty;

@Component
public class PenaltyMapper {
    
    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PenaltyDTO toDTO(Penalty penalty) {
        if (penalty == null) {
            return null;
        }

        String startDate = penalty.getPenaltyStartDate().format(FORMATTER_BR);
        String endDate = penalty.getPenaltyEndDate().format(FORMATTER_BR);
        return new PenaltyDTO(penalty.getId(), penalty.getPenaltyReason(), startDate, endDate);
    }

    public Penalty toEntity(PenaltyDTO penaltyDTO) { 
        if (penaltyDTO == null) {
            return null;
        }

        Penalty penalty = new Penalty();

        if (penaltyDTO.id() != null) {
            penalty.setId(penaltyDTO.id());
        }
        penalty.setPenaltyReason(penaltyDTO.penaltyReason());
        penalty.setPenaltyStartDate(parseDate(penaltyDTO.penaltyStartDate()));
        penalty.setPenaltyEndDate(parseDate(penaltyDTO.penaltyEndDate()));

        return penalty;
    }

    public LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, FORMATTER_BR);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException(dateString, e);
        }
    }
}
