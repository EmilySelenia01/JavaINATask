package com.example.ms_productos.common.exceptions;

public class ProductoConStockException extends RuntimeException {

    public ProductoConStockException() {
    }

    public ProductoConStockException(String message) {
        super(message);
    }

    public ProductoConStockException(
            String nombreProducto,
            Integer stock
    ) {
        super(
                "No se puede eliminar el producto "
                        + nombreProducto
                        + " porque todavía tiene "
                        + stock
                        + " unidades en stock"
        );
    }

    public ProductoConStockException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}