package com.uneb.labweb.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.enums.Status;
import com.uneb.labweb.enums.converters.AppointmentStatusConverter;
import com.uneb.labweb.enums.converters.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    
    //@FutureOrPresent
    @NotNull
    @Column(length = 25, nullable = false)
    private LocalDateTime appointmentDateTime;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = AppointmentStatusConverter.class)
    private AppointmentStatus appointmentStatus;

    // @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)//, optional = false)
    @JoinColumn(name = "doctor_id")//, nullable = false)
    private Doctor doctor;

    // @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)//, optional = false)
    @JoinColumn(name = "health_center_id")//, nullable = false)
    private HealthCenter healthCenter;

    // @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)//, optional = false)
    @JoinColumn(name = "specialty_id")//, nullable = false)
    private Specialty specialty;

    // @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)//, optional = false)
    @JoinColumn(name = "user_id")//, nullable = false)
    private User user;
    
    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;
}
