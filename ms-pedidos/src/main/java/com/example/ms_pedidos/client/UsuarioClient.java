package com.example.ms_pedidos.client;

import com.example.ms_pedidos.dto.usuario.UsuarioDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UsuarioClient {

    private final RestClient restClient;
    private final String usuarioUrl;

    public UsuarioClient(
            RestClient restClient,
            @Value("${microservicios.usuarios.url}") String usuarioUrl) {

        this.restClient = restClient;
        this.usuarioUrl = usuarioUrl;
    }

    public UsuarioDTO obtenerUsuario(Long id) {

        return restClient
                .get()
                .uri(usuarioUrl + "/api/usuarios/{id}", id)
                .retrieve()
                .body(UsuarioDTO.class);
    }

}//END class
