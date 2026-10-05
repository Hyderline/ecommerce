package com.simoneg.ecommerce.utils;

import com.simoneg.ecommerce.utils.exceptions.FieldNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class myExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errori = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errori.putIfAbsent(e.getField(), e.getDefaultMessage()));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", "Validation failed");
        body.put("error", errori);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(FieldNotAuthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleFieldNotAuthorized(FieldNotAuthorizedException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 403);
        body.put("message", ex.getMessage());
        body.put("field", ex.getField());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
}
