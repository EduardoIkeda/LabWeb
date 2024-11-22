package com.uneb.labweb.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.uneb.labweb.enums.Status;
import com.uneb.labweb.enums.converters.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@SQLDelete(sql = "UPDATE Appointment SET status = 'Inativo' WHERE id = ?")
@SQLRestriction("status = 'Ativo'")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @FutureOrPresent
    @NotNull
    @Column(length = 25, nullable = false)
    private LocalDateTime appointmentDateTime;

    @Column(nullable = true)
    private Boolean attended;

    // Adicionar relacionamento
    // @NotNull
    // @Column
    // private User user;
    
    // // Adicionar relacionamento
    // @NotNull
    // @Column
    // private HealthCenter healthCenter;
    
    // // Adicionar relacionamento
    // @NotNull
    // @Column
    // private Specialty specialty;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;
}
