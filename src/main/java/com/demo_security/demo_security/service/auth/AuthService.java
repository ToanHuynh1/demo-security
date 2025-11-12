package com.demo_security.demo_security.service.auth;

import com.demo_security.demo_security.payload.LoginRequest;
import com.demo_security.demo_security.payload.RefreshTokenRequest;
import com.demo_security.demo_security.payload.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(LoginRequest loginRequest);
    ResponseEntity<?> refreshToken(RefreshTokenRequest request);
    ResponseEntity<?> register(RegisterRequest signUpRequest);
}
