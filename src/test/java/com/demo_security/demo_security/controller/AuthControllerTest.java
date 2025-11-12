package com.demo_security.demo_security.controller;

import com.demo_security.demo_security.payload.auth.LoginRequest;
import com.demo_security.demo_security.payload.auth.RegisterRequest;
import com.demo_security.demo_security.payload.auth.RefreshTokenRequest;
import com.demo_security.demo_security.service.auth.AuthService;
import com.demo_security.demo_security.controller.auth.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class AuthControllerTest {
    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        ResponseEntity response = ResponseEntity.ok("success");
        when(authService.login(loginRequest)).thenReturn(response);
        ResponseEntity result = authController.authenticateUser(loginRequest);
        assertEquals(response, result);
        verify(authService, times(1)).login(loginRequest);
    }


    @Test
    void testRefreshToken() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        ResponseEntity response = ResponseEntity.ok("refreshed");
        when(authService.refreshToken(refreshTokenRequest)).thenReturn(response);
        ResponseEntity result = authController.refreshToken(refreshTokenRequest);
        assertEquals(response, result);
        verify(authService, times(1)).refreshToken(refreshTokenRequest);
    }


    @Test
    void testRegisterUser() {
        RegisterRequest registerRequest = new RegisterRequest();
        ResponseEntity response = ResponseEntity.ok("registered");
        when(authService.register(registerRequest)).thenReturn(response);
        ResponseEntity result = authController.registerUser(registerRequest);
        assertEquals(response, result);
        verify(authService, times(1)).register(registerRequest);
    }
}
