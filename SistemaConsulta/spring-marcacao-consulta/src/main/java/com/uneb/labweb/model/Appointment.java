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

    // @NotNull
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "doctor_id", nullable = false)
    // private Doctor doctor;

    // @NotNull
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "health_center_id", nullable = false)
    // private HealthCenter healthCenter;

    // @NotNull
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "specialty_id", nullable = false)
    // private Specialty specialty;

    // @NotNull
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "user_id", nullable = false)
    // private User user;
    
    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;
}
