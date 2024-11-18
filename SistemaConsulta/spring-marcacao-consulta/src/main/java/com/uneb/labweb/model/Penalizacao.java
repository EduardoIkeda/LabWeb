package com.uneb.labweb.model;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//Validações ainda genéricas

@Data
@Entity
public class Penalizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private Long userId;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private LocalDateTime dataInicioPenalizacao;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private LocalDateTime dataFimPenalizacao;
}
