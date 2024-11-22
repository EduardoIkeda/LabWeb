package com.uneb.labweb.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record DoctorDTO(
    Long id,

    @Pattern(regexp = "^[1-9]\\d{0,5}-(A[CLPM]|BA|CE|DF|ES|GO|M[ATSG]|P[ABREI]|R[JNSOR]|S[CPE]|TO)$") // Ex: 12345-BA
    @NotBlank
    @Length(min = 4, max = 9)
    String crm,

    @Pattern(regexp = "^\\d{2}:\\d{2}$") // Ex: 08:00
    @NotBlank
    @Length(min = 5, max = 5)
    String startWork,

    @Pattern(regexp = "^\\d{2}:\\d{2}$") // Ex: 18:00
    @NotBlank
    @Length(min = 5, max = 5)
    String endWork,

    @NotEmpty
    List<String> workingDays
) {

}
