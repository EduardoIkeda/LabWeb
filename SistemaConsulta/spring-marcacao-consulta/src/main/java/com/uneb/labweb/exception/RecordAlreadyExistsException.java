package com.uneb.labweb.exception;

public class RecordAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistsException() {
        super("Registro já existente");
    }

    public RecordAlreadyExistsException(Long id) {
        super("Registro já existente com o id: " + id);
    }
}
