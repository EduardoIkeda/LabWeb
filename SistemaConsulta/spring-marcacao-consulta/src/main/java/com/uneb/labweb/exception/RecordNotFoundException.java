package com.uneb.labweb.exception;

public class RecordNotFoundException extends RuntimeException {

    // Serial version UID para garantir a compatibilidade de versão
    private static final long serialVersionUID = 1L;

    // Construtor padrão com uma mensagem genérica
    public RecordNotFoundException() {
        super("Registro não encontrado");
    }

    // Construtor que recebe um ID e cria uma mensagem personalizada
    public RecordNotFoundException(Long id) {
        super("Registro não encontrado com o id: " + id);
    } 

    // Construtor que recebe uma mensagem personalizada
    public RecordNotFoundException(String message) {
        super(message);
    }
}
