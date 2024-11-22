package com.uneb.labweb.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthResponseDTO(
        @NotBlank
        String name,
        
        @NotBlank
        String token
) {

}
