package com.uneb.labweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthResponseDTO(
    @NotBlank @NotNull String name, 
    @NotBlank @NotNull String token
) {

}
