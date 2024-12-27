package com.uneb.labweb.exception;

public class WrongPasswordException extends RuntimeException {

    // Serial version UID para garantir a compatibilidade de versão
    private static final long serialVersionUID = 1L;

    // Construtor padrão com uma mensagem genérica
    public WrongPasswordException() {
        super("Senha incorreta");
    }

    // Construtor que recebe uma mensagem personalizada
    public WrongPasswordException(String message) {
        super("Senha incorreta: " + message);
    }
}
