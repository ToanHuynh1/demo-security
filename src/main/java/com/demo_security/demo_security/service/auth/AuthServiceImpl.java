package com.demo_security.demo_security.service.auth;

import java.util.EnumSet;
import com.demo_security.demo_security.model.User;
import com.demo_security.demo_security.payload.auth.JwtResponse;
import com.demo_security.demo_security.payload.auth.LoginRequest;
import com.demo_security.demo_security.payload.auth.RefreshTokenRequest;
import com.demo_security.demo_security.payload.auth.RegisterRequest;
import com.demo_security.demo_security.security.JwtUtils;
import com.demo_security.demo_security.service.user.UserService;
import com.demo_security.demo_security.model.RoleConstants;
import com.demo_security.demo_security.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
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

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenRequest request) {
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

    @Override
    public ResponseEntity<?> register(RegisterRequest signUpRequest) {
        String role = signUpRequest.getRole();
        if (role == null) {
            role = RoleConstants.USER;
        } else if (!role.equalsIgnoreCase(RoleConstants.ADMIN) && !role.equalsIgnoreCase(RoleConstants.USER)) {
            return ResponseEntity.badRequest().body("Error: Role must be USER or ADMIN!");
        } else {
            role = role.toUpperCase();
        }

        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
      
        EnumSet<Permission> permissions;
        if (role.equals(RoleConstants.ADMIN)) {
            permissions = EnumSet.of(Permission.ADMIN_DASHBOARD_VIEW, Permission.USER_PROFILE_VIEW);
        } else {
            permissions = EnumSet.of(Permission.USER_PROFILE_VIEW);
        }
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .role(role)
                .permissions(permissions)
                .build();
        userService.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}
