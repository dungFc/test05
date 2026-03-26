package com.example.lab14.service;

import com.example.lab14.dto.request.AuthRequest;
import com.example.lab14.dto.request.RegisterRequest;
import com.example.lab14.dto.response.AuthResponse;

public interface AuthService {
    void register(RegisterRequest request);

    AuthResponse login(AuthRequest request);
}

