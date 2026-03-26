package com.example.tuan3.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public <T> ApiResponse<T> handleCustom(CustomException ex){
        return new ApiResponse<>(400, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ApiResponse<T> handleValidation(MethodArgumentNotValidException ex){
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ApiResponse<>(400, msg, null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public <T> ApiResponse<T> handle405(HttpRequestMethodNotSupportedException ex){
        return new ApiResponse<>(405, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public <T> ApiResponse<T> handleOther(Exception ex){
        return new ApiResponse<>(500, ex.getMessage(), null);
    }
}