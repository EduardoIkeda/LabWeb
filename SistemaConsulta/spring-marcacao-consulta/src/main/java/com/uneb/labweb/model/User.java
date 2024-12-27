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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uneb.labweb.enums.Role;
import com.uneb.labweb.enums.Status;
import com.uneb.labweb.enums.UserStatus;
import com.uneb.labweb.enums.converters.RoleConverter;
import com.uneb.labweb.enums.converters.StatusConverter;
import com.uneb.labweb.enums.converters.UserStatusConverter;

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

    // Número do cartão SUS (válido se tiver 15 dígitos)
    @Pattern(regexp = "^\\d{15}$") // Ex: 012345678901234
    @NotBlank
    @Length(min = 15, max = 15)
    @Column(length = 15, nullable = false)
    private String susCardNumber;

    // Nome do usuário
    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    // CPF do usuário (válido se tiver 11 dígitos)
    @Pattern(regexp = "^\\d{11}$") // Ex: 01234567890
    @NotBlank
    @Length(min = 11, max = 11)
    @Column(length = 11, nullable = false)
    private String cpf;

    // Telefone do usuário (válido se tiver entre 10 e 12 dígitos)
    @Pattern(regexp = "^\\d{10,12}$") // Ex: 71982345678, 7136485678
    @NotBlank
    @Length(min = 10, max = 12)
    @Column(length = 12, nullable = false)
    private String phone;

    // E-mail do usuário
    @Email
    @NotBlank
    @Length(min = 6, max = 100)
    @Column(length = 100, nullable = false)
    private String email;

    // Senha do usuário
    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String password;

    // URL do avatar do usuário
    @Length(min = 5, max = 400)
    @Column(length = 400, nullable = false)
    private String avatarUrl;

    // Status do usuário (Ativo/Inativo)
    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = UserStatusConverter.class)
    private UserStatus userStatus = UserStatus.ACTIVE;

    // Papel do usuário (Admin, Doutor, Cidadão)
    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;

    // Relacionamento com consultas
    @Valid
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments = new ArrayList<>();

    // Relacionamento com médico (caso o usuário seja um doutor)
    @JsonManagedReference
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = true)
    private Doctor doctor;

    // Relacionamento com penalidades
    @Valid
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Penalty> penalties = new ArrayList<>();

    // Status de exclusão lógica (Ativo/Inativo)
    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

    // Métodos da interface UserDetails

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
