package com.hospital.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==============================
    // RESOURCE NOT FOUND
    // ==============================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", ex.getMessage());
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // ==============================
    // RESOURCE ALREADY EXISTS
    // ==============================
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExists(
            ResourceAlreadyExistsException ex,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", ex.getMessage());
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ==============================
    // VALIDATION ERROR
    // ==============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ==============================
    // ACCESS DENIED (403)
    // ==============================
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("error", "You do not have permission to access this resource");
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // ==============================
    // GENERIC ERROR
    // ==============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", ex.getMessage());
        response.put("path", request.getRequestURI());

        // print error in backend console
        ex.printStackTrace();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}