package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.PenalizacaoDTO;
import com.uneb.labweb.model.Penalizacao;

@Component
public class PenalizacaoMapper {
    
    public PenalizacaoDTO toDTO(Penalizacao pen) {
        if (pen == null) {
            return null;
        }

        return new PenalizacaoDTO(pen.getId(), pen.getUserId(), pen.getDataInicioPenalizacao(), pen.getDataFimPenalizacao());
    }

    //public Penalizacao toEntity(PenalizacaoDTO penDTO) { }
}
