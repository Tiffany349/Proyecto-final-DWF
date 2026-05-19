package com.infinitisky.service;

import com.infinitisky.dto.request.LoginRequest;
import com.infinitisky.dto.request.RegisterRequest;
import com.infinitisky.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}