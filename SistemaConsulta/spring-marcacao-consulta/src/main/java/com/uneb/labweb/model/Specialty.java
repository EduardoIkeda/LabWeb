package com.uneb.labweb.model;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@SQLDelete(sql = "UPDATE Specialty SET status = 'Inativo' WHERE id = ?")
@SQLRestriction("status = 'Ativo'")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    @Length(min = 5, max = 255)
    @Column(length = 255, nullable = false)
    private String description;

    // @Valid
    // @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Appointment> appointments = new ArrayList<>();

    // @Valid
    // @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, orphanRemoval = true)
    // private Set<Doctor> doctors = new HashSet<>();

    // @Valid
    // @ManyToMany(mappedBy = "specialties")
    // private Set<HealthCenter> healthCenters = new HashSet<>();

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;
}
