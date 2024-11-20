package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.AppointmentDTO;
import com.uneb.labweb.model.Appointment;

@Component
public class AppointmentMapper {

    public AppointmentDTO toDTO(Appointment consulta) {
        if (consulta == null) {
            return null;
        }

        return new AppointmentDTO(consulta.getId(), consulta.getUserId(), consulta.getPostoSaudeId(), consulta.getEspecialidadeId(), consulta.getDataConsulta(), consulta.isCompareceu());
    }
    
    //public Consulta toEntity(ConsultaDTO consultaDTO) { }
}
