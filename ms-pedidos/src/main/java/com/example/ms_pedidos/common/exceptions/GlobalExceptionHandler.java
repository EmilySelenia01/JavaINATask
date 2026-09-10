package com.example.ms_pedidos.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ApiError> handlePedidoNotFound(
            PedidoNotFoundException ex) {

        ApiError error = crearError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CantidadExcedeLimiteException.class)
    public ResponseEntity<ApiError> handleCantidadExcedeLimite(
            CantidadExcedeLimiteException ex) {

        ApiError error = crearError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({
            LimitePedidosActivosException.class,
            PedidoYaCanceladoException.class
    })
    public ResponseEntity<ApiError> handleConflict(RuntimeException ex) {

        ApiError error = crearError(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex) {

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatearError)
                .toList();

        ApiError error = crearError(
                HttpStatus.BAD_REQUEST,
                "Hay errores en los datos enviados",
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ApiError> handleErrorDeOtroMicroservicio(
            RestClientResponseException ex) {

        HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());

        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        String mensaje = ex.getResponseBodyAsString();

        if (mensaje == null || mensaje.isBlank()) {
            mensaje = "El otro microservicio respondió con un error";
        }

        ApiError error = crearError(
                status,
                mensaje,
                null
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex) {

        ApiError error = crearError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno en el servidor",
                null
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    private ApiError crearError(
            HttpStatus status,
            String message,
            List<String> details) {

        return new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                details
        );
    }

    private String formatearError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }

}//END class