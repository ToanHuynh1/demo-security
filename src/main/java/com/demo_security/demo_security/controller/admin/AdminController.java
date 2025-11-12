package com.demo_security.demo_security.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin dashboard endpoints")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get admin dashboard", description = "Access admin dashboard (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Dashboard accessed successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - admin role required")
    public String adminDashboard() {
        return "Admin dashboard: only ADMIN can access";
    }
}
