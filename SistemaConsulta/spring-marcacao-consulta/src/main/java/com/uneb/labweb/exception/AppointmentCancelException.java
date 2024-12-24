package com.uneb.labweb.exception;

public class AppointmentCancelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AppointmentCancelException() {
        super("Não foi possível cancelar a consulta.");
    }

    public AppointmentCancelException(String message) {
        super(message);
    }

}
