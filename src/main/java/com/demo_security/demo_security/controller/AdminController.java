
package com.demo_security.demo_security.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/admin")
public class AdminController {
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Admin dashboard: only ADMIN can access";
    }
}
