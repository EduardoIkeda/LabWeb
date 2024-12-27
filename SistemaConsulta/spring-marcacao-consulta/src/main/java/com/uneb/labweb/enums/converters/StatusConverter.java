package com.uneb.labweb.enums.converters;

import java.util.stream.Stream;

import com.uneb.labweb.enums.Status;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    /**
     * Converte o valor do atributo Status para o formato de banco de dados (String).
     * 
     * @param status O status a ser convertido.
     * @return O valor do status como uma String.
     */
    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    /**
     * Converte o valor do banco de dados (String) de volta para o tipo Status.
     * 
     * @param value O valor do status como String a ser convertido.
     * @return O status correspondente do tipo Status.
     * @throws IllegalArgumentException Se o valor fornecido não corresponder a um valor válido do enum.
     */
    @Override
    public Status convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(Status.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
