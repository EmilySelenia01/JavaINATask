package com.taller.ms_usuarios.common.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {

    //es la forma estandar de como enviarmos los errores
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private List<String> details;

    public ApiError(LocalDateTime timestamp, int status, String error, String message, List<String> details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.details = details;
    }

    public ApiError(LocalDateTime timestamp, int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

}//END class
