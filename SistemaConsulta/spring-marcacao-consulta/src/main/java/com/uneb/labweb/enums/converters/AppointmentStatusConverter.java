package com.uneb.labweb.enums.converters;

import java.util.stream.Stream;

import com.uneb.labweb.enums.AppointmentStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AppointmentStatusConverter implements AttributeConverter<AppointmentStatus, String> {

    /**
     * Converte o valor do atributo AppointmentStatus para o formato de banco de dados (String).
     * 
     * @param status O status da consulta a ser convertido.
     * @return O valor do status como uma String.
     */
    @Override
    public String convertToDatabaseColumn(AppointmentStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }
    
    /**
     * Converte o valor do banco de dados (String) de volta para o tipo AppointmentStatus.
     * 
     * @param value O valor do status como String a ser convertido.
     * @return O status correspondente do tipo AppointmentStatus.
     * @throws IllegalArgumentException Se o valor fornecido não corresponder a um valor válido do enum.
     */
    @Override
    public AppointmentStatus convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(AppointmentStatus.values())
                .filter(s -> s.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
