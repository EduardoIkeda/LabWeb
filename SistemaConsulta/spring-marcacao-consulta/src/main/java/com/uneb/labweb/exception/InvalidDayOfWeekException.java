package com.uneb.labweb.exception;

public class InvalidDayOfWeekException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public InvalidDayOfWeekException(String message, Throwable cause) {
        super("Dia da semana inválido: " + message + "\nErro:" + cause);
    }    
}
