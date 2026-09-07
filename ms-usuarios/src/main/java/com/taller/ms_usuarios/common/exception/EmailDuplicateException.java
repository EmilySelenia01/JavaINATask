package com.taller.ms_usuarios.common.exception;

public class EmailDuplicateException extends RuntimeException {

    public EmailDuplicateException() {
    }
    public EmailDuplicateException(String message) {
        super("Ya existe un usuario registrado con el email: " + message
        );
    }
    public EmailDuplicateException(String message, Throwable cause) {

    }

}//END class
