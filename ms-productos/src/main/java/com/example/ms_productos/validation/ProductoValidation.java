package com.example.ms_productos.validation;

import com.example.ms_productos.common.exceptions.NombreDuplicadoException;
import com.example.ms_productos.common.exceptions.ProductoConStockException;
import com.example.ms_productos.common.exceptions.StockInsuficienteException;
import com.example.ms_productos.model.Producto;
import com.example.ms_productos.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoValidation {

    // Aplica las reglas de negocio relacionadas con los productos.
    private final ProductoRepository productoRepository;

    public void validarNombreUnicoParaCreacion(String nombre) {

        // Comprueba si ya existe un producto con ese nombre,
        // ignorando mayúsculas y minúsculas.
        if (productoRepository.existsByNombreIgnoreCase(nombre)) {
            throw new NombreDuplicadoException(nombre);
        }
    }

    public void validarNombreUnicoParaActualizacion(
            Long productoId,
            String nombre
    ) {

        // Permite conservar el nombre del producto que se está actualizando,
        // pero evita utilizar el nombre de otro producto.
        if (productoRepository.existsByNombreIgnoreCaseAndIdNot(
                nombre,
                productoId
        )) {
            throw new NombreDuplicadoException(nombre);
        }
    }

    public void validarStockSuficiente(
            Producto producto,
            Integer cantidad
    ) {

        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad que se desea descontar debe ser mayor que cero"
            );
        }

        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(
                    producto.getNombre(),
                    producto.getStock(),
                    cantidad
            );
        }
    }

    public void validarProductoSinStock(Producto producto) {

        if (producto.getStock() > 0) {
            throw new ProductoConStockException(
                    producto.getNombre(),
                    producto.getStock()
            );
        }
    }

    public void validarCantidadReposicion(Integer cantidad) {

        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad que se desea reponer debe ser mayor que cero"
            );
        }
    }
}