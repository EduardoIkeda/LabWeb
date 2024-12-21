package com.uneb.labweb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.uneb.labweb.enums.Role;
import com.uneb.labweb.enums.Status;
import com.uneb.labweb.enums.converters.RoleConverter;
import com.uneb.labweb.enums.converters.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Table(name = "users")
@Entity
@SQLDelete(sql = "UPDATE users SET status = 'Inativo' WHERE id = ?")
@SQLRestriction("status = 'Ativo'")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^\\d{15}$") // Ex: 012345678901234
    @NotBlank
    @Length(min = 15, max = 15)
    @Column(length = 15, nullable = false)
    private String susCardNumber;

    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @Pattern(regexp = "^\\d{11}$") // Ex: 01234567890
    @NotBlank
    @Length(min = 11, max = 11)
    @Column(length = 11, nullable = false)
    private String cpf;

    @Pattern(regexp = "^(55)?\\d{10,11}$") // Ex: 5571982345678, 557136485678, 71982345678, 7136485678
    @NotBlank
    @Length(min = 10, max = 13)
    @Column(length = 13, nullable = false)
    private String phone;

    @Email
    @NotBlank
    @Length(min = 6, max = 100)
    @Column(length = 100, nullable = false)
    private String email;

    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String password;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;

    @Valid
    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = true)
    private Doctor doctor;

    @Valid
    @OneToMany(mappedBy = "user")
    private List<Penalty> penalties = new ArrayList<>();

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (this.role) {
            case Role.ADMIN -> List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_DOCTOR"),
                new SimpleGrantedAuthority("ROLE_CITIZEN")
            );
            case Role.DOCTOR -> List.of(
                new SimpleGrantedAuthority("ROLE_DOCTOR"),
                new SimpleGrantedAuthority("ROLE_CITIZEN")
            );
            default -> List.of(
                new SimpleGrantedAuthority("ROLE_CITIZEN")
            );
        };
    }

    @Override
    public String getUsername() {
        return susCardNumber;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
