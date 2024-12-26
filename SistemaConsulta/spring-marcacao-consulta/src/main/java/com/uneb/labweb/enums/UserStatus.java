package com.uneb.labweb.enums;

public enum UserStatus {
    ACTIVE("Ativo"),
    BLOCKED("Bloqueado");
    
    private final String value;

    private UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}