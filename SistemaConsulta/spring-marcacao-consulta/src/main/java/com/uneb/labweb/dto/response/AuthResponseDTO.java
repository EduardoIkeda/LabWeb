package com.uneb.labweb.dto.response;

import jakarta.validation.constraints.NotBlank;

public record AuthResponseDTO(
        @NotBlank
        String name,

        @NotBlank
        String role,
        
        @NotBlank
        String token
) {

}
