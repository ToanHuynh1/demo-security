package com.demo_security.demo_security.service.auth;

import com.demo_security.demo_security.payload.auth.LoginRequest;
import com.demo_security.demo_security.payload.auth.RefreshTokenRequest;
import com.demo_security.demo_security.payload.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(LoginRequest loginRequest);
    ResponseEntity<?> refreshToken(RefreshTokenRequest request);
    ResponseEntity<?> register(RegisterRequest signUpRequest);
}
