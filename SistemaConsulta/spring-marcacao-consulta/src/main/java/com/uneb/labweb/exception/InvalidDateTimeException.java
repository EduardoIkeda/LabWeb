package com.uneb.labweb.exception;

public class InvalidDateTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public InvalidDateTimeException(String message, Throwable cause) {
        super("Data e hora são inválidas: " + message + "\nErro:" + cause);
    }
}
