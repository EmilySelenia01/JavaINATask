package com.example.ms_pedidos.controller;

import com.example.ms_pedidos.dto.pedido.PedidoRequestDTO;
import com.example.ms_pedidos.dto.pedido.PedidoResponseDTO;
import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(
            @Valid @RequestBody PedidoRequestDTO request) {

        PedidoResponseDTO pedidoCreado =
                pedidoService.crearPedido(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoCreado);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {

        return ResponseEntity.ok(
                pedidoService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerDetalle(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.obtenerDetalle(id)
        );
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.cancelarPedido(id)
        );
    }

}//END class
