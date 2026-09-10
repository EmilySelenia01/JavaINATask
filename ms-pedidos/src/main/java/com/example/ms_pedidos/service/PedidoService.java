package com.example.ms_pedidos.service;

import com.example.ms_pedidos.client.ProductoClient;
import com.example.ms_pedidos.client.UsuarioClient;
import com.example.ms_pedidos.common.enums.EstadoPedido;
import com.example.ms_pedidos.common.exceptions.PedidoNotFoundException;
import com.example.ms_pedidos.dto.pedido.PedidoRequestDTO;
import com.example.ms_pedidos.dto.pedido.PedidoResponseDTO;
import com.example.ms_pedidos.dto.producto.ProductoDTO;
import com.example.ms_pedidos.dto.usuario.UsuarioDTO;
import com.example.ms_pedidos.mapper.PedidoMapper;
import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.repository.PedidoRepository;
import com.example.ms_pedidos.validation.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final PedidoValidator pedidoValidator;
    private final UsuarioClient usuarioClient;
    private final ProductoClient productoClient;

    public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {

        // 1. Validar que la cantidad no sea mayor a 20
        pedidoValidator.validarCantidadMaxima(request.getCantidad());

        // 2. Validar el límite de pedidos confirmados del usuario
        pedidoValidator.validarLimitePedidosActivos(
                request.getUsuarioId()
        );

        // 3. Consultar el usuario en ms-usuarios
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(
                request.getUsuarioId()
        );

        // 4. Consultar el producto en ms-productos
        ProductoDTO producto = productoClient.obtenerProducto(
                request.getProductoId()
        );

        // 5. Calcular precio por cantidad
        BigDecimal total = producto.getPrecio().multiply(
                BigDecimal.valueOf(request.getCantidad())
        );

        // 6. Solicitar a ms-productos que descuente el stock
        productoClient.descontarStock(
                request.getProductoId(),
                request.getCantidad()
        );

        // 7. Construar y guardar el pedido confirmado
        Pedido pedido = pedidoMapper.toEntity(request);

        pedido.setTotal(total);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.CONFIRMADO);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // 8. Construir la respuesta combinada
        return pedidoMapper.toResponse(
                pedidoGuardado,
                usuario,
                producto
        );
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public PedidoResponseDTO obtenerDetalle(Long id) {

        Pedido pedido = buscarPedido(id);

        UsuarioDTO usuario = usuarioClient.obtenerUsuario(
                pedido.getUsuarioId()
        );

        ProductoDTO producto = productoClient.obtenerProducto(
                pedido.getProductoId()
        );

        return pedidoMapper.toResponse(
                pedido,
                usuario,
                producto
        );
    }

    public Pedido cancelarPedido(Long id) {

        // 1. Buscar el pedido
        Pedido pedido = buscarPedido(id);

        // 2. Verificar que no esté cancelado
        pedidoValidator.validarPedidoNoCancelado(pedido);

        // 3. Reponer el stock antes de cambiar el estado
        productoClient.reponerStock(
                pedido.getProductoId(),
                pedido.getCantidad()
        );

        // 4. Cambiar el estado y guardar
        pedido.setEstado(EstadoPedido.CANCELADO);

        return pedidoRepository.save(pedido);
    }

    private Pedido buscarPedido(Long id) {

        return pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new PedidoNotFoundException(id)
                );
    }

}//END class
