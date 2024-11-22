package com.uneb.labweb.exception;

public class InvalidDateTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public InvalidDateTimeException(String message, Throwable cause) {
        super("A data e hora fornecida são inválidas: " + message + "\nErro:" + cause);
    }
}
