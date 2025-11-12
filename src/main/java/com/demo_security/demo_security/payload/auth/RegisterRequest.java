package com.demo_security.demo_security.payload.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // Thêm role để chọn khi đăng ký (USER, ADMIN)
}
