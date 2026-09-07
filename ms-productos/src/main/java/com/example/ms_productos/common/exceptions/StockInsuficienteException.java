package com.example.ms_productos.common.exceptions;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException() {
    }

    public StockInsuficienteException(String message) {
        super(message);
    }

    public StockInsuficienteException(
            String nombreProducto,
            Integer stockDisponible,
            Integer cantidadSolicitada
    ) {
        super(
                "El producto " + nombreProducto
                        + " no tiene stock suficiente. Stock disponible: "
                        + stockDisponible
                        + ", cantidad solicitada: "
                        + cantidadSolicitada
        );
    }

    public StockInsuficienteException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}
