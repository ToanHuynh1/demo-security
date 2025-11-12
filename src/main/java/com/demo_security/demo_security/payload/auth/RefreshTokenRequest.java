package com.demo_security.demo_security.payload.auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
