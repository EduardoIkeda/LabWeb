package com.uneb.labweb.model;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//Validações ainda genéricas

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String susCardNumber;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String cpf;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String phone;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String email;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String password;
}
