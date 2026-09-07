package com.example.ms_productos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private boolean stockBajo;
}