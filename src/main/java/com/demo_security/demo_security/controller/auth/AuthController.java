package com.demo_security.demo_security.controller.auth;

import com.demo_security.demo_security.payload.auth.LoginRequest;
import com.demo_security.demo_security.payload.auth.RegisterRequest;
import jakarta.validation.Valid;
import com.demo_security.demo_security.payload.auth.RefreshTokenRequest;
import com.demo_security.demo_security.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API endpoints for user authentication and token management")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "User login", description = "Authenticate user and get JWT access token and refresh token")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "Refresh access token", description = "Generate a new access token using the refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }


    @Operation(summary = "User registration", description = "Register a new user account")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        return authService.register(signUpRequest);
    }
}
