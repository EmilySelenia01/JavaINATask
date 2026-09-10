package com.example.ms_pedidos.client;
import com.example.ms_pedidos.dto.producto.ProductoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductoClient {

    private final RestClient restClient;
    private final String productoUrl;

    public ProductoClient(
            RestClient restClient,
            @Value("${microservicios.productos.url}") String productoUrl) {

        this.restClient = restClient;
        this.productoUrl = productoUrl;
    }

    public ProductoDTO obtenerProducto(Long id) {

        return restClient
                .get()
                .uri(productoUrl + "/api/productos/{id}", id)
                .retrieve()
                .body(ProductoDTO.class);
    }

    public void descontarStock(Long productoId, int cantidad) {

        restClient
                .patch()
                .uri(
                        productoUrl
                                + "/api/productos/{id}/descontar-stock?cantidad={cantidad}",
                        productoId,
                        cantidad
                )
                .retrieve()
                .toBodilessEntity();
    }

    public void reponerStock(Long productoId, int cantidad) {

        restClient
                .patch()
                .uri(
                        productoUrl
                                + "/api/productos/{id}/reponer-stock?cantidad={cantidad}",
                        productoId,
                        cantidad
                )
                .retrieve()
                .toBodilessEntity();
    }

}//END class