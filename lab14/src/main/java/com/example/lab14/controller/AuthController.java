package com.example.lab14.controller;

import com.example.lab14.dto.request.AuthRequest;
import com.example.lab14.dto.request.RegisterRequest;
import com.example.lab14.dto.response.AuthResponse;
import com.example.lab14.exception.ApiResponse;
import com.example.lab14.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Register success"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(authService.login(request))
        );
    }
}
