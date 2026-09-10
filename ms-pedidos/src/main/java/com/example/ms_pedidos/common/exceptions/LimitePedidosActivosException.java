package com.example.ms_pedidos.common.exceptions;

public class LimitePedidosActivosException extends RuntimeException {

    public LimitePedidosActivosException(Long usuarioId) {
        super("El usuario con id " + usuarioId
                + " ya alcanzó el límite de 5 pedidos confirmados");
    }

}
