package com.uneb.labweb.enums.converters;

import java.util.stream.Stream;

import com.uneb.labweb.enums.AppointmentStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AppointmentStatusConverter implements AttributeConverter<AppointmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(AppointmentStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }
    
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
