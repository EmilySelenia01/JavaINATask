package com.taller.ms_usuarios.controller;

import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.model.Usuario;
import com.taller.ms_usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String obtener() {
        return "aqui estoy";
    }

    @GetMapping("/test/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String obtenerById(@PathVariable Long id) {
        return "aqui estoy id: " + id.toString();
    }

    @PostMapping()
    public ResponseEntity<UsuarioResponseDTO> crear(
            @Valid @RequestBody UsuarioRequestDTO usuarioDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.crearUser(usuarioDTO));

    }//END method

    @GetMapping("/findAll")
    public ResponseEntity<List<UsuarioResponseDTO>> listAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                usuarioService.findById(id)
        );
    }


}//END class
