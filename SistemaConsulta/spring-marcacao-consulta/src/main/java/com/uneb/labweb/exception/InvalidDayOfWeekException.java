package com.uneb.labweb.exception;

public class InvalidDayOfWeekException extends RuntimeException {

    // Serial version UID para garantir a compatibilidade de versão
    private static final long serialVersionUID = 1L;

    // Construtor que recebe uma mensagem e a causa da exceção
    public InvalidDayOfWeekException(String message, Throwable cause) {
        super("Dia da semana é inválido: " + message + "\nErro:" + cause);
    }    
}
