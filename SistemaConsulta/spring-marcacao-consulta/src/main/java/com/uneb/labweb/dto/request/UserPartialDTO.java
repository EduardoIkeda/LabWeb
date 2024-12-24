package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserPartialDTO(
        @Pattern(regexp = "^(55)?\\d{10,11}$") // Ex: 5571982345678, 557136485678, 71982345678, 7136485678 
        String phone,

        @Email  
        @Length(min = 6, max = 100) 
        String email,

        @Length(min = 5, max = 100) 
        String password
) {
        
}
