package com.uneb.labweb.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthResponseDTO(
        @NotNull
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String avatarUrl,

        @NotBlank
        String role,
        
        @NotBlank
        String token
) {

}
