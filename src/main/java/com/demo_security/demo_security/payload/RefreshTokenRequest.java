package com.demo_security.demo_security.payload;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
