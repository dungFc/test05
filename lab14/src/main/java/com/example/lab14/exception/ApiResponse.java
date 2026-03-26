package com.example.lab14.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T>{
    private int code;
    private String message;
    private T data;

    // SUCCESS
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    // ERROR - không có data
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    // ERROR - có data (vd: validation errors)
    public static <T> ApiResponse<T> error(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    // VALIDATION riêng (optional - clean hơn)
    public static <T> ApiResponse<T> validationError(T data) {
        return new ApiResponse<>(400, "Validation failed", data);
    }
}
