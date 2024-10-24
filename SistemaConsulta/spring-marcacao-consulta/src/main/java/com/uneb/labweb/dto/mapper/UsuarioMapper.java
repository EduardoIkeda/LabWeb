package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.UsuarioDTO;
import com.uneb.labweb.model.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario user) {
        if (user == null) {
            return null;
        }

        return new UsuarioDTO(user.getId(), user.getNumeroCartaoSus(), user.getNome(), user.getCpf(), user.getTelefone(), user.getEmail(), user.getSenha());
    }

    public Usuario toEntity(UsuarioDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        Usuario user = new Usuario();

        if (userDTO.id() != null) {
            user.setId(userDTO.id());
        }
        user.setNumeroCartaoSus(userDTO.numeroCartaoSus());
        user.setNome(userDTO.nome());        
        user.setCpf(userDTO.cpf());
        user.setTelefone(userDTO.telefone());
        user.setEmail(userDTO.email());
        user.setSenha(userDTO.senha());
        
        return user;
    }   
}
