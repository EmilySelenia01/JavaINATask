package com.example.ms_productos.service;


import com.example.ms_productos.common.exceptions.ProductoNoEncontradoException;
import com.example.ms_productos.dto.ProductoRequestDTO;
import com.example.ms_productos.dto.ProductoResponseDTO;

import com.example.ms_productos.mapper.ProductoMapper;
import com.example.ms_productos.model.Producto;
import com.example.ms_productos.repository.ProductoRepository;
import com.example.ms_productos.validation.ProductoValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ProductoValidation productoValidator;

    public List<ProductoResponseDTO> findAll() {

        List<Producto> lista = productoRepository.findAll();

        return productoMapper.toResponseList(lista);
    }

    public ProductoResponseDTO findById(Long id) {

        Producto producto = buscarProductoPorId(id);

        return productoMapper.toResponse(producto);
    }

    public ProductoResponseDTO crearProducto(
            ProductoRequestDTO productoDTO
    ) {

        // Primero se aplica la regla de negocio.
        productoValidator.validarNombreUnicoParaCreacion(
                productoDTO.getNombre()
        );

        // Después se convierte el DTO en una entidad.
        Producto producto = productoMapper.toEntity(productoDTO);

        // Se guarda la entidad en la base de datos.
        producto = productoRepository.save(producto);

        // Finalmente, se convierte la entidad guardada en un ResponseDTO.
        return productoMapper.toResponse(producto);
    }

    public ProductoResponseDTO actualizarProducto(
            Long id,
            ProductoRequestDTO productoDTO
    ) {

        // Busca el producto que se quiere actualizar.
        Producto producto = buscarProductoPorId(id);

        // Comprueba que el nuevo nombre no pertenezca a otro producto.
        productoValidator.validarNombreUnicoParaActualizacion(
                id,
                productoDTO.getNombre()
        );

        // Actualiza la entidad existente.
        productoMapper.updateEntity(producto, productoDTO);

        // Guarda los cambios.
        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

    public ProductoResponseDTO descontarStock(
            Long id,
            Integer cantidad
    ) {

        Producto producto = buscarProductoPorId(id);

        // Comprueba que la cantidad sea válida y exista stock suficiente.
        productoValidator.validarStockSuficiente(
                producto,
                cantidad
        );

        producto.setStock(
                producto.getStock() - cantidad
        );

        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

    public ProductoResponseDTO reponerStock(
            Long id,
            Integer cantidad
    ) {

        Producto producto = buscarProductoPorId(id);

        productoValidator.validarCantidadReposicion(cantidad);

        producto.setStock(
                producto.getStock() + cantidad
        );

        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

    public void eliminarProducto(Long id) {

        Producto producto = buscarProductoPorId(id);

        // No permite eliminar productos que todavía tengan stock.
        productoValidator.validarProductoSinStock(producto);

        productoRepository.delete(producto);
    }

    private Producto buscarProductoPorId(Long id) {

        return productoRepository.findById(id)
                .orElseThrow(
                        () -> new ProductoNoEncontradoException(id)
                );
    }
}
