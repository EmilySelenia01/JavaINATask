package com.taller.ms_usuarios.common.exception;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(Integer id) {
        super("No se encontró el usuario con id: " + id);
    }

}//END class
