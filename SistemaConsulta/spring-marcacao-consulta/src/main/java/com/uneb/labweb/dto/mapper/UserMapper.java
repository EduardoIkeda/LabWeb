package com.uneb.labweb.dto.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.UserDTO;
import com.uneb.labweb.enums.Role;
import com.uneb.labweb.model.User;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    /**
     * Construtor para injeção de dependência do PasswordEncoder.
     * @param passwordEncoder O PasswordEncoder utilizado para codificar senhas.
     */
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Converte uma entidade User para um DTO de UserDTO.
     * @param user A entidade User a ser convertida.
     * @return O UserDTO correspondente.
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        // Cria e retorna o DTO com os dados do usuário
        return new UserDTO(
            user.getId(), 
            user.getSusCardNumber(), 
            user.getName(), 
            user.getCpf(), 
            user.getPhone(), 
            user.getEmail(), 
            user.getPassword(),
            user.getAvatarUrl(),
            user.getUserStatus().getValue(),
            user.getRole().getValue());
    }

    /**
     * Converte um DTO de UserDTO para a entidade User.
     * @param userDTO O DTO de User a ser convertido.
     * @return A entidade User correspondente.
     */
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();

        if (userDTO.id() != null) {
            user.setId(userDTO.id());
        }
        user.setSusCardNumber(userDTO.susCardNumber());
        user.setName(userDTO.name());        
        user.setCpf(userDTO.cpf());
        user.setPhone(userDTO.phone());
        user.setEmail(userDTO.email());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setAvatarUrl(userDTO.avatarUrl());
        user.setRole(convertRoleValue(userDTO.role()));        
        
        return user;
    }
    
    /**
     * Converte um valor de String para um enum Role.
     * @param value O valor da role a ser convertido.
     * @return O enum Role correspondente.
     */
    public Role convertRoleValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "admin" -> Role.ADMIN;
            case "doctor" -> Role.DOCTOR;
            case "citizen" -> Role.CITIZEN;
            default -> throw new IllegalArgumentException("Role inválido: " + value);
        };    
    }
}
