package com.demo_security.demo_security.payload.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
