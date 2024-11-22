package com.uneb.labweb.model;

import java.time.LocalTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import com.uneb.labweb.enums.Status;
import com.uneb.labweb.enums.converters.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@SQLDelete(sql = "UPDATE HealthCenter SET status = 'Inativo' WHERE id = ?")
@SQLRestriction("status = 'Ativo'")
public class HealthCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String address;

    @NotNull
    @Column(length = 15, nullable = false)
    private LocalTime openingHour;

    @NotNull
    @Column(length = 15, nullable = false)
    private LocalTime closingHour;

    // Adicionar relacionamento
    // @NotNull
    // @Column
    // private Specialty specialties;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

    @AssertTrue(message = "O horário de abertura deve ser anterior ao horário de encerramento.")
    public boolean isOpeningBeforeClosing() {
        return openingHour.isBefore(closingHour);
    }
}
