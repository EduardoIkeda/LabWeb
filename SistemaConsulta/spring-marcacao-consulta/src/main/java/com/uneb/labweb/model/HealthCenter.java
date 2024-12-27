package com.uneb.labweb.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uneb.labweb.enums.Status;
import com.uneb.labweb.enums.converters.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@SQLDelete(sql = "UPDATE HEALTH_CENTER SET status = 'Inativo' WHERE id = ?")
@SQLRestriction("status = 'Ativo'")
public class HealthCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Nome do posto de saúde
    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;
    
    // Endereço do posto de saúde
    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String address;
    
    // Horário de abertura do posto de saúde
    @NotNull
    @Column(length = 15, nullable = false)
    private LocalTime openingHour;
    
    // Horário de encerramento do posto de saúde
    @NotNull
    @Column(length = 15, nullable = false)
    private LocalTime closingHour;
    
    // Lista de consultas associadas ao posto de saúde
    @Valid
    @JsonManagedReference
    @OneToMany(mappedBy = "healthCenter")
    private List<Appointment> appointments = new ArrayList<>();
    
    // Lista de médicos associados ao posto de saúde
    @Valid
    @JsonManagedReference
    @ManyToMany(mappedBy = "healthCenters")
    private List<Doctor> doctors = new ArrayList<>();
    
    // Lista de especialidades oferecidas no posto de saúde
    @Valid
    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "health_center_specialty",
            joinColumns = @JoinColumn(name = "health_center_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private List<Specialty> specialties = new ArrayList<>();
    
    // Status do posto de saúde (Ativo/Inativo)
    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;
    
    // Validação para garantir que o horário de abertura é anterior ao horário de fechamento
    @AssertTrue(message = "O horário de abertura deve ser anterior ao horário de encerramento.")
    public boolean isOpeningBeforeClosing() {
        return openingHour.isBefore(closingHour);
    }
}
