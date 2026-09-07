package com.example.ms_productos.mapper;


import com.example.ms_productos.dto.ProductoRequestDTO;
import com.example.ms_productos.dto.ProductoResponseDTO;
import com.example.ms_productos.model.Producto;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface ProductoMapper {

    // Convierte el RequestDTO en una entidad.
    @Mapping(target = "id", ignore = true)
    Producto toEntity(ProductoRequestDTO dto);

    // Convierte una entidad en un ResponseDTO.
    ProductoResponseDTO toResponse(Producto producto);

    // Actualiza la entidad con los datos recibidos en el DTO.
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void updateEntity(
            @MappingTarget Producto producto,
            ProductoRequestDTO dto
    );

    // Convierte una lista de entidades en una lista de ResponseDTO.
    List<ProductoResponseDTO> toResponseList(
            List<Producto> productos
    );
}
