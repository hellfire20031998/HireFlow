package com.hellFire.HireFlowEmailService.controllers;

import com.hellFire.HireFlowEmailService.dtos.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(500)
                .body(ApiResponse.error("INTERNAL_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Invalid request");

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("VALIDATION_ERROR", errorMessage));
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ApiResponse<?>> handleMessagingException(MessagingException ex) {
        return ResponseEntity
                .status(500)
                .body(ApiResponse.error("EMAIL_SEND_FAILED", "Failed to send email"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(ApiResponse.error("UNKNOWN_ERROR", ex.getMessage()));
    }
}
