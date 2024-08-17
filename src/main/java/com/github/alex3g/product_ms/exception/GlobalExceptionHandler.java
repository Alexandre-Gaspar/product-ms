package com.github.alex3g.product_ms.exception;

import com.github.alex3g.product_ms.exception.custom.ProductValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorException> handleMethodArgumentNotValidException(MethodArgumentNotValidException notValidException, HttpServletRequest servletRequest) {
        Map<String, String> errors = new HashMap<>();

        notValidException.getBindingResult().getAllErrors()
                .forEach((error) -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        StandardErrorException errorBuilder = StandardErrorException.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .path(servletRequest.getRequestURI())
                .messages(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBuilder);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductValidationException.class)
    public ResponseEntity<StandardErrorException> handleProductValidationException(ProductValidationException exception, HttpServletRequest servletRequest) {
        StandardErrorException errorBuilder = StandardErrorException.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .path(servletRequest.getRequestURI())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBuilder);
    }
}
