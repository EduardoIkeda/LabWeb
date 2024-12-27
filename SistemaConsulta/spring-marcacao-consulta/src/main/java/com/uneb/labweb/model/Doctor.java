package com.uneb.labweb.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uneb.labweb.enums.Status;
import com.uneb.labweb.enums.converters.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@SQLDelete(sql = "UPDATE Doctor SET status = 'Inativo' WHERE id = ?")
@SQLRestriction("status = 'Ativo'")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // CRM do médico
    @Pattern(regexp = "^[1-9]\\d{0,5}-(A[CLPM]|BA|CE|DF|ES|GO|M[ATSG]|P[ABREI]|R[JNSOR]|S[CPE]|TO)$") 
    @NotBlank
    @Length(min = 4, max = 9)
    @Column(length = 9, nullable = false)
    private String crm;
    
    // Horário de início de trabalho
    @NotNull
    @Column(length = 15, nullable = false)
    private LocalTime startWork;
    
    // Horário de fim de trabalho
    @NotNull
    @Column(length = 15, nullable = false)
    private LocalTime endWork;
    
    // Dias da semana que o médico trabalha
    @NotEmpty
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<DayOfWeek> workingDays = new HashSet<>();
    
    // Lista de consultas agendadas para o médico
    @Valid
    @JsonManagedReference
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();
    
    // postos de saúde associados ao médico
    @Valid
    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "doctor_health_center",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "health_center_id"))
    private List<HealthCenter> healthCenters = new ArrayList<>();
    
    // Especialidades associadas ao médico
    @Valid
    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "doctor_specialty",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private List<Specialty> specialties = new ArrayList<>();
    
    // Usuário associado ao médico (relacionamento OneToOne)
    @NotNull
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // Status do médico, que pode ser "Ativo" ou "Inativo"
    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;
    
    // Validação para garantir que o horário de início de trabalho é antes do horário de término
    @AssertTrue(message = "O horário de começo do trabalho deve ser anterior ao horário de encerramento do trabalho.")
    public boolean isStartWorkBeforeEndWork() {
        return startWork.isBefore(endWork);
    }
}
