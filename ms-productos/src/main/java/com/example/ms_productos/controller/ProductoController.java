package com.example.ms_productos.controller;


import com.example.ms_productos.dto.ProductoRequestDTO;
import com.example.ms_productos.dto.ProductoResponseDTO;
import com.example.ms_productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAll() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(
            @PathVariable Long id
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(
            @Valid @RequestBody ProductoRequestDTO productoDTO
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoService.crearProducto(productoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO productoDTO
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        productoService.actualizarProducto(
                                id,
                                productoDTO
                        )
                );
    }

    @PatchMapping("/{id}/descontar-stock")
    public ResponseEntity<ProductoResponseDTO> descontarStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        productoService.descontarStock(
                                id,
                                cantidad
                        )
                );
    }

    @PatchMapping("/{id}/reponer-stock")
    public ResponseEntity<ProductoResponseDTO> reponerStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        productoService.reponerStock(
                                id,
                                cantidad
                        )
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        productoService.eliminarProducto(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
