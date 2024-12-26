package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserPartialDTO(
        @Length(min = 5, max = 400)
        String avatarUrl,

        @Pattern(regexp = "^\\d{10,12}$") // Ex: 71982345678, 7136485678 
        String phone,

        @Email  
        @Length(min = 6, max = 100) 
        String email,

        @Length(min = 5, max = 100) 
        String password
) {
        
}
