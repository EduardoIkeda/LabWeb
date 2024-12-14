package com.uneb.labweb.exception;

public class WrongPasswordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WrongPasswordException() {
        super("Senha incorreta");
    }

    public WrongPasswordException(String message) {
        super("Senha incorreta: " + message);
    }
}
