package com.uneb.labweb.model;

import java.time.LocalDate;

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
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@SQLDelete(sql = "UPDATE Penalty SET status = 'Inativo' WHERE id = ?")
@SQLRestriction("status = 'Ativo'")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String penaltyReason;

    @FutureOrPresent
    @NotNull
    @Column(length = 15, nullable = false)
    private LocalDate penaltyStartDate;

    @FutureOrPresent
    @NotNull
    @Column(length = 15, nullable = false)
    private LocalDate penaltyEndDate;

    // // Adicionar relacionamento
    // @NotBlank
    // @Length(min = 5, max = 100)
    // @Column(length = 100, nullable = false)
    // private User user;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

    @AssertTrue(message = "A data de início deve ser igual ou anterior à data de término.")
    public boolean isPenaltyDatesValid() {
        return !penaltyEndDate.isBefore(penaltyStartDate);
    }
}
