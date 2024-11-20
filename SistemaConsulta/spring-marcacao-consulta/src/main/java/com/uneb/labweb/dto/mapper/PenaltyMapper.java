package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.PenaltyDTO;
import com.uneb.labweb.model.Penalty;

@Component
public class PenaltyMapper {
    
    public PenaltyDTO toDTO(Penalty penalty) {
        if (penalty == null) {
            return null;
        }

        return new PenaltyDTO(penalty.getId(), penalty.getDataInicioPenalizacao(), penalty.getDataFimPenalizacao());
    }

    //public Penalty toEntity(PenaltyDTO penaltyDTO) { }
}
