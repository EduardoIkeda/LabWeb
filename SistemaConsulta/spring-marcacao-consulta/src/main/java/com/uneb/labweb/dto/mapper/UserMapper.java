package com.uneb.labweb.dto.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.request.UserDTO;
import com.uneb.labweb.enums.Role;
import com.uneb.labweb.model.User;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getId(), user.getSusCardNumber(), user.getName(), user.getCpf(), user.getPhone(), user.getEmail(), user.getPassword(), user.getRole().getValue());
    }

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
        user.setRole(convertRoleValue(userDTO.role()));        
        
        return user;
    }
    
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
