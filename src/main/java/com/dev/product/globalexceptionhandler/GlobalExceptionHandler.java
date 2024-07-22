package com.dev.product.globalexceptionhandler;

import com.dev.product.errorresponse.CustomErrorResponse;
import com.dev.product.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler class that handles exceptions across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the ResourceNotFoundException and returns a custom error response.
     *
     * @param ex the ResourceNotFoundException instance
     * @return a ResponseEntity with the custom error response and HTTP status NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now().toString(),
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles the MethodArgumentNotValidException and returns a custom error response.
     *
     * @param ex the MethodArgumentNotValidException instance
     * @return a ResponseEntity with the custom error response and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}