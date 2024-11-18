package com.uneb.labweb.dto.mapper;

import org.springframework.stereotype.Component;

import com.uneb.labweb.dto.UserDTO;
import com.uneb.labweb.model.User;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getId(), user.getSusCardNumber(), user.getName(), user.getCpf(), user.getPhone(), user.getEmail(), user.getPassword());
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
        user.setPassword(userDTO.password());
        
        return user;
    }   
}
