package com.uneb.labweb.enums.converters;

import java.util.stream.Stream;

import com.uneb.labweb.enums.Role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    /**
     * Converte o valor do atributo Role para o formato de banco de dados (String).
     * 
     * @param role O papel (Role) a ser convertido.
     * @return O valor do papel como uma String.
     */
    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.getValue();
    }

    /**
     * Converte o valor do banco de dados (String) de volta para o tipo Role.
     * 
     * @param value O valor do papel como String a ser convertido.
     * @return O papel correspondente do tipo Role.
     * @throws IllegalArgumentException Se o valor fornecido não corresponder a um valor válido do enum.
     */
    @Override
    public Role convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(Role.values())
                .filter(r -> r.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
