package com.demo_security.demo_security.controller.auth;

import com.demo_security.demo_security.payload.auth.LoginRequest;
import com.demo_security.demo_security.payload.auth.RegisterRequest;
import com.demo_security.demo_security.payload.auth.RefreshTokenRequest;
import com.demo_security.demo_security.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest signUpRequest) {
        return authService.register(signUpRequest);
    }
}
