package com.taller.ms_usuarios.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
/*
Intercepta todas las excepciones que me
van a llegar
es un responsy by
esta clase es la que me permite capturar cualquier excepcion
 no tengo que aplicar los try catch en los
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<ApiError> handleEmailDuplicado(EmailDuplicateException ex) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }//END method - error personalizado correo

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidaciones(MethodArgumentNotValidException ex) {

        List<String> listError
                = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Uno o mas campos no son validos",
                listError
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }//END method - errores de valid


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleError(Exception ex) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocurrio un error inesperado: "+ ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }//END method - cualquier tipo de error

}//END class
