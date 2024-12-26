package com.uneb.labweb.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(
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

        @Pattern(regexp = "^\\d{10,12}$") // Ex: 71982345678, 7136485678
        @NotBlank 
        String phone,

        @Email 
        @NotBlank 
        @Length(min = 6, max = 100) 
        String email,

        @NotBlank 
        @Length(min = 5, max = 100) 
        String password
) {

}
