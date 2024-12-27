package com.uneb.labweb.enums.converters;

import java.util.stream.Stream;

import com.uneb.labweb.enums.UserStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    /**
     * Converte o valor do atributo UserStatus para o formato de banco de dados (String).
     * 
     * @param userStatus O status do usuário a ser convertido.
     * @return O valor do status como uma String.
     */
    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        if (userStatus == null) {
            return null;
        }
        return userStatus.getValue();
    }

    /**
     * Converte o valor do banco de dados (String) de volta para o tipo UserStatus.
     * 
     * @param value O valor do status como String a ser convertido.
     * @return O status correspondente do tipo UserStatus.
     * @throws IllegalArgumentException Se o valor fornecido não corresponder a um valor válido do enum.
     */
    @Override
    public UserStatus convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(UserStatus.values())
                .filter(us -> us.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}