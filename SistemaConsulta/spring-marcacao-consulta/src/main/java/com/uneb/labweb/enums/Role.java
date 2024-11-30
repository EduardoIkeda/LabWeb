package com.uneb.labweb.enums;

public enum Role {
    ADMIN("admin"),
    DOCTOR("doctor"),
    CITIZEN("citizen");
    
    private final String value;

    private Role(String value) {
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
