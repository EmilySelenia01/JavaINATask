package com.example.ms_pedidos.common.exceptions;

public class PedidoYaCanceladoException extends RuntimeException {

    public PedidoYaCanceladoException(Long id) {
        super("El pedido con id " + id + " ya se encuentra cancelado");
    }
}