package com.example.ms_pedidos.repository;

import com.example.ms_pedidos.common.enums.EstadoPedido;
import com.example.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    long countByUsuarioIdAndEstado(
            Long usuarioId,
            EstadoPedido estado
    );

}
