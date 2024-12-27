package com.uneb.labweb.exception;

public class AppointmentCancelException extends RuntimeException {

    // Serial version UID para garantir a compatibilidade de versão
    private static final long serialVersionUID = 1L;

    // Construtor sem parâmetros, com mensagem padrão
    public AppointmentCancelException() {
        super("Não foi possível cancelar a consulta.");
    }

    // Construtor com mensagem personalizada
    public AppointmentCancelException(String message) {
        super(message);
    }
}
