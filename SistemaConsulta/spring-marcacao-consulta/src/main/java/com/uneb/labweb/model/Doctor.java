package com.uneb.labweb.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

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

    @Pattern(regexp = "^[1-9]\\d{0,5}-(A[CLPM]|BA|CE|DF|ES|GO|M[ATSG]|P[ABREI]|R[JNSOR]|S[CPE]|TO)$") // Ex: 12345-BA
    @NotBlank
    @Length(min = 4, max = 9)
    @Column(length = 9, nullable = false)
    private String crm;

    @NotNull
    @Column(length = 15, nullable = false)
    private LocalTime startWork;

    @NotNull
    @Column(length = 15, nullable = false)
    private LocalTime endWork;

    @NotEmpty
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<DayOfWeek> workingDays = new HashSet<>();

    // @Valid
    // @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Appointment> appointments = new ArrayList<>();

    // @Valid
    // @ManyToMany
    // @JoinTable(name = "doctor_health_center",
    //         joinColumns = @JoinColumn(name = "doctor_id"),
    //         inverseJoinColumns = @JoinColumn(name = "health_center_id"))
    // private Set<HealthCenter> healthCenters = new HashSet<>();

    // @NotNull
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "specialty_id", nullable = false)
    // private Specialty specialty;

    // @NotNull
    // @OneToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "user_id", nullable = false)
    // private User user;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

    @AssertTrue(message = "O horário de começo do trabalho deve ser anterior ao horário de encerramento do trabalho.")
    public boolean isStartWorkBeforeEndWork() {
        return startWork.isBefore(endWork);
    }
}
