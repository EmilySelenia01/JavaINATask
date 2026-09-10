package com.example.ms_pedidos.common.exceptions;

public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException(Long id) {
        super("No se encontró el pedido con id: " + id);
    }

}
