package com.example.ms_pedidos.validation;

import com.example.ms_pedidos.common.enums.EstadoPedido;
import com.example.ms_pedidos.common.exceptions.CantidadExcedeLimiteException;
import com.example.ms_pedidos.common.exceptions.LimitePedidosActivosException;
import com.example.ms_pedidos.common.exceptions.PedidoYaCanceladoException;
import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.repository.PedidoRepository;
import org.springframework.stereotype.Component;

@Component
public class PedidoValidator {

    private static final int CANTIDAD_MAXIMA = 20;
    private static final long LIMITE_PEDIDOS_ACTIVOS = 5;

    private final PedidoRepository pedidoRepository;

    public PedidoValidator(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void validarCantidadMaxima(Integer cantidad) {

        if (cantidad > CANTIDAD_MAXIMA) {
            throw new CantidadExcedeLimiteException(cantidad);
        }
    }

    public void validarLimitePedidosActivos(Long usuarioId) {

        long pedidosConfirmados = pedidoRepository
                .countByUsuarioIdAndEstado(
                        usuarioId,
                        EstadoPedido.CANCELADO
                );

        if (pedidosConfirmados >= LIMITE_PEDIDOS_ACTIVOS) {
            throw new LimitePedidosActivosException(usuarioId);
        }
    }

    public void validarPedidoNoCancelado(Pedido pedido) {

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new PedidoYaCanceladoException(pedido.getId());
        }
    }

}//END class
