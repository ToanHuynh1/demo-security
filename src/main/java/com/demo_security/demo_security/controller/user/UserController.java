package com.demo_security.demo_security.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "User profile endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @GetMapping("/profile")
    @Operation(summary = "Get user profile", description = "Retrieve current user profile information")
    @ApiResponse(responseCode = "200", description = "Profile retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - token invalid or missing")
    public String userProfile() {
        return "User profile: USER hoặc ADMIN đều truy cập được";
    }
}
