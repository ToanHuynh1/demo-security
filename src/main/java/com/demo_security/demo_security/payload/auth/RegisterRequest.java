package com.demo_security.demo_security.payload.auth;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Username không được để trống")
    private String username;
    @NotBlank(message = "Password không được để trống")
    private String password;
    @NotBlank(message = "Role không được để trống")
    private String role; // Thêm role để chọn khi đăng ký (USER, ADMIN)
}
