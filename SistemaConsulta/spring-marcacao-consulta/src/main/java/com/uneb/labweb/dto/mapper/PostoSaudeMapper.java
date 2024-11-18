package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.PostoSaudeDTO;
import com.uneb.labweb.model.PostoSaude;

@Component
public class PostoSaudeMapper {

    public PostoSaudeDTO toDTO(PostoSaude posto) {
        if (posto == null) {
            return null;
        }

        return new PostoSaudeDTO(posto.getId(), posto.getNome(), posto.getEndereco(), posto.getEspecialidades(), posto.getHorarioFuncionamento());
    }
    
    //public PostoSaude toEntity(PostoSaudeDTO postoDTO) { }
}
