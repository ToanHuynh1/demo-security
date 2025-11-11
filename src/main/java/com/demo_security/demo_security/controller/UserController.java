
package com.demo_security.demo_security.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/profile")
    public String userProfile() {
        return "User profile: USER hoặc ADMIN đều truy cập được";
    }
}
