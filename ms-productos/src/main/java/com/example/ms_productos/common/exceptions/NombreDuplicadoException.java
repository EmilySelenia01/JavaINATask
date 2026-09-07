package com.example.ms_productos.common.exceptions;

public class NombreDuplicadoException extends RuntimeException {

    public NombreDuplicadoException() {
    }

    public NombreDuplicadoException(String nombre) {
        super(
                "Ya existe un producto registrado con el nombre: "
                        + nombre
        );
    }

    public NombreDuplicadoException(
            String nombre,
            Throwable cause
    ) {
        super(
                "Ya existe un producto registrado con el nombre: "
                        + nombre,
                cause
        );
    }
}