package com.example.ms_pedidos.common.exceptions;

public class CantidadExcedeLimiteException extends RuntimeException {

    public CantidadExcedeLimiteException(Integer cantidad) {
        super("La cantidad solicitada excede el límite permitido de 20 unidades. Cantidad solicitada: "
                + cantidad);
    }

}
