package com.uneb.labweb.enums;

public enum Role {
    ADMIN("admin"),
    DOCTOR("doctor"),
    CITIZEN("citizen");
    
    private final String value;

    // Construtor para inicializar o valor do papel
    private Role(String value) {
        this.value = value;
    }

    // Getter para acessar o valor associado ao papel
    public String getValue() {
        return value;
    }

    // Sobrescrita do método toString para retornar o valor como string
    @Override
    public String toString() {
        return value;
    }
}
