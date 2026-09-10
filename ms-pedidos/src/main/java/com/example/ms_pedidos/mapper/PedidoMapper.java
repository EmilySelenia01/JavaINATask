package com.example.ms_pedidos.mapper;

import com.example.ms_pedidos.dto.pedido.PedidoRequestDTO;
import com.example.ms_pedidos.dto.pedido.PedidoResponseDTO;
import com.example.ms_pedidos.dto.producto.ProductoDTO;
import com.example.ms_pedidos.dto.usuario.UsuarioDTO;
import com.example.ms_pedidos.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "total", ignore = true),
            @Mapping(target = "fecha", ignore = true),
            @Mapping(target = "estado", ignore = true)
    })
    Pedido toEntity(PedidoRequestDTO request);

    @Mappings({
            @Mapping(target = "id", source = "pedido.id"),
            @Mapping(target = "usuario", source = "usuario"),
            @Mapping(target = "producto", source = "producto"),
            @Mapping(target = "cantidad", source = "pedido.cantidad"),
            @Mapping(target = "total", source = "pedido.total"),
            @Mapping(target = "fecha", source = "pedido.fecha"),
            @Mapping(target = "estado", source = "pedido.estado")
    })
    PedidoResponseDTO toResponse(
            Pedido pedido,
            UsuarioDTO usuario,
            ProductoDTO producto
    );

}//END interface