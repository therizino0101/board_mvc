package com.example.stepboard.post.common.error;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.stepboard.post.common.exception.PostNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    // Front단에 에러 메시지를 띄워주기 위함.
    // IllegalStateException, DB Exception, 잘못된 JSON Exception(요청 단계)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage())
        );
        return buildResponse(
            HttpStatus.BAD_REQUEST,
            "INVALID_INPUT",
            "입력값을 확인해 주세요",
            fieldErrors
        );
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(PostNotFoundException exception) {
        return buildResponse(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", exception.getMessage(), Map.of());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        for(ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            int lastDotIndex = propertyPath.lastIndexOf(".");
            String fieldName = lastDotIndex >= 0
                ? propertyPath.substring(lastDotIndex + 1)
                : propertyPath;
            fieldErrors.put(fieldName, violation.getMessage());
        }
        return buildResponse(
            HttpStatus.BAD_REQUEST,
            "INVALID_INPUT",
            "입력값을 확인해주세요.",
            fieldErrors
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
        HttpStatus status,
        String code,
        String message,
        Map<String, String> fieldErrors
    ) {
        ErrorResponse response = new ErrorResponse(
            status.value(),
            code,
            message,
            fieldErrors
        );
        return ResponseEntity.status(status).body(response);
    }
}
