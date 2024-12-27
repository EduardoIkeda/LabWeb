package com.uneb.labweb.exception;

public class InvalidDateTimeException extends RuntimeException {

    // Serial version UID para garantir a compatibilidade de versão
    private static final long serialVersionUID = 1L;

    // Construtor que recebe uma mensagem e a causa da exceção
    public InvalidDateTimeException(String message, Throwable cause) {
        super("Data e hora são inválidas: " + message + "\nErro:" + cause);
    }
}
