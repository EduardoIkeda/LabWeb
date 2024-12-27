package com.uneb.labweb.enums;

public enum AppointmentStatus {
    PENDING("pending"),
    SCHEDULED("scheduled"),
    ATTENDED("attended"),
    MISSED("missed");
    
    private final String value;

    // Construtor para inicializar o valor do status
    private AppointmentStatus(String value) {
        this.value = value;
    }

    // Getter para acessar o valor associado ao status
    public String getValue() {
        return value;
    }

    // Sobrescrita do método toString para retornar o valor como string
    @Override
    public String toString() {
        return value;
    }
}
