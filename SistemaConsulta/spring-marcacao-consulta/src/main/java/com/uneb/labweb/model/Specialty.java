package com.uneb.labweb.model;

import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
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

    @Valid
    @OneToMany(mappedBy = "specialty")
    private List<Appointment> appointments = new ArrayList<>();

    @Valid
    @ManyToMany(mappedBy = "specialties")
    private List<Doctor> doctors = new ArrayList<>();

    @Valid
    @ManyToMany
    @JoinTable(name = "health_center_specialty",
            joinColumns = @JoinColumn(name = "specialty_id"),
            inverseJoinColumns = @JoinColumn(name = "health_center_id"))
    private List<HealthCenter> healthCenters = new ArrayList<>();

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;
}
