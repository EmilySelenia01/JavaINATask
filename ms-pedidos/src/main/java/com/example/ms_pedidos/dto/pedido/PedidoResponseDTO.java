package com.example.ms_pedidos.dto.pedido;

import com.example.ms_pedidos.common.enums.EstadoPedido;
import com.example.ms_pedidos.dto.producto.ProductoDTO;
import com.example.ms_pedidos.dto.usuario.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    private Long id;
    private UsuarioDTO usuario;
    private ProductoDTO producto;
    private Integer cantidad;
    private BigDecimal total;
    private LocalDateTime fecha;
    private EstadoPedido estado;

}//END class