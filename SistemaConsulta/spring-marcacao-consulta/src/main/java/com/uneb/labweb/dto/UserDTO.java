package com.uneb.labweb.dto;

import org.hibernate.validator.constraints.Length;

import com.uneb.labweb.enums.Role;
import com.uneb.labweb.enums.validation.ValueOfEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        Long id,

        @Pattern(regexp = "^\\d{15}$") // Ex: 012345678901234
        @NotBlank 
        @Length(min = 15, max = 15) 
        String susCardNumber,

        @NotBlank 
        @Length(min = 5, max = 100) 
        String name,

        @Pattern(regexp = "^\\d{11}$") // Ex: 01234567890
        @NotBlank 
        @Length(min = 11, max = 11) 
        String cpf,

        @Pattern(regexp = "^(55)?\\d{10,11}$") // Ex: 5571982345678, 557136485678, 71982345678, 7136485678
        @NotBlank 
        @Length(min = 10, max = 13) 
        String phone,

        @Email 
        @NotBlank 
        @Length(min = 6, max = 100) 
        String email,

        @NotBlank 
        @Length(min = 5, max = 100) 
        String password,

        @NotBlank 
        @Length(min = 3, max = 10)
        @ValueOfEnum(enumClass = Role.class)
        String role
) {
    
}
