package com.uneb.labweb.exception;

public class RecordAlreadyExistsException extends RuntimeException {

    // Serial version UID para garantir a compatibilidade de versão
    private static final long serialVersionUID = 1L;

    // Construtor padrão que usa uma mensagem genérica
    public RecordAlreadyExistsException() {
        super("Registro já existente");
    }

    // Construtor que recebe um ID e cria uma mensagem personalizada
    public RecordAlreadyExistsException(Long id) {
        super("Registro já existente com o id: " + id);
    }
}
