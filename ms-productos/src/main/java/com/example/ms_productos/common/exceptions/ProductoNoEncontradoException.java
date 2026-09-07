package com.example.ms_productos.common.exceptions;

public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException() {
    }

    public ProductoNoEncontradoException(Long id) {
        super("No se encontró el producto con el id: " + id);
    }

    public ProductoNoEncontradoException(
            Long id,
            Throwable cause
    ) {
        super(
                "No se encontró el producto con el id: " + id,
                cause
        );
    }
}