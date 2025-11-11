package com.demo_security.demo_security.controller;

import com.demo_security.demo_security.model.User;
import com.demo_security.demo_security.service.UserService;
import com.demo_security.demo_security.payload.LoginRequest;
import com.demo_security.demo_security.payload.RegisterRequest;
import com.demo_security.demo_security.payload.JwtResponse;
import com.demo_security.demo_security.payload.RefreshTokenRequest;
import com.demo_security.demo_security.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getUsername());
    long refreshExpiry = System.currentTimeMillis() + jwtUtils.getRefreshExpirationMs();
    userService.updateRefreshToken(loginRequest.getUsername(), refreshToken, refreshExpiry);
    User user = userService.findByUsername(loginRequest.getUsername()).get();
    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken, user.getUsername(), user.getRole()));
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        if (!jwtUtils.validateRefreshToken(refreshToken)) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        User user = userService.findByUsername(username).orElse(null);
        if (user == null || user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
            return ResponseEntity.badRequest().body("Refresh token not found or mismatched");
        }
        if (user.getRefreshTokenExpiry() == null || user.getRefreshTokenExpiry() < System.currentTimeMillis()) {
            return ResponseEntity.badRequest().body("Refresh token expired");
        }
        String newAccessToken = jwtUtils.generateJwtToken(username);
        String newRefreshToken = jwtUtils.generateRefreshToken(username);
        long newRefreshExpiry = System.currentTimeMillis() + jwtUtils.getRefreshExpirationMs();
        userService.updateRefreshToken(username, newRefreshToken, newRefreshExpiry);
        return ResponseEntity.ok(new JwtResponse(newAccessToken, newRefreshToken, user.getUsername(), user.getRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        String role = signUpRequest.getRole();
        if (role == null || (!role.equalsIgnoreCase("ADMIN") && !role.equalsIgnoreCase("USER"))) {
            role = "USER";
        } else {
            role = role.toUpperCase();
        }
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .role(role)
                .build();
        userService.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}
