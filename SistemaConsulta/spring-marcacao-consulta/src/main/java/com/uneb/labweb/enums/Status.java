package com.uneb.labweb.enums;

public enum Status {
    ACTIVE("Ativo"), 
    INACTIVE("Inativo");
    
    private final String value;

    // Construtor para inicializar o valor do status
    private Status(String value) {
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
