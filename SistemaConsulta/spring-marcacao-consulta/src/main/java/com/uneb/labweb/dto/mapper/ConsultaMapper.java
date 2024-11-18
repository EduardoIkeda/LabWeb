package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.ConsultaDTO;
import com.uneb.labweb.model.Consulta;

@Component
public class ConsultaMapper {

    public ConsultaDTO toDTO(Consulta consulta) {
        if (consulta == null) {
            return null;
        }

        return new ConsultaDTO(consulta.getId(), consulta.getUserId(), consulta.getPostoSaudeId(), consulta.getEspecialidadeId(), consulta.getDataConsulta(), consulta.isCompareceu());
    }
    
    //public Consulta toEntity(ConsultaDTO consultaDTO) { }
}
