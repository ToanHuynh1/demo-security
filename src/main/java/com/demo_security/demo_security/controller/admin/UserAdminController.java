package com.demo_security.demo_security.controller.admin;

import com.demo_security.demo_security.model.Permission;
import com.demo_security.demo_security.model.User;
import com.demo_security.demo_security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.demo_security.demo_security.payload.user.UserSearchCriteria;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "User Admin", description = "User management endpoints for administrators")
public class UserAdminController {
    @Autowired
    private UserService userService;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all users", description = "Retrieve all users (admin only, paginated, searchable)")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(required = false) String username,
                                    @RequestParam(required = false) String role,
                                    @RequestParam(defaultValue = "id,asc") String sort) {
        UserSearchCriteria criteria = new UserSearchCriteria();
        criteria.setUsername(username);
        criteria.setRole(role);
        String[] sortParams = sort.split(",");
        Sort sortObj = Sort.by(Sort.Direction.fromString(sortParams.length > 1 ? sortParams[1] : "asc"), sortParams[0]);
        return ResponseEntity.ok(userService.searchUsers(criteria, page, size, sortObj));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_MANAGE') or hasRole('ADMIN')")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by ID (admin only)")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('USER_MANAGE') or hasRole('ADMIN')")
    @Operation(summary = "Delete user", description = "Delete a user (admin only)")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('USER_MANAGE') or hasRole('ADMIN')")
    @Operation(summary = "Update user", description = "Update user information (admin only)")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.update(id, user);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/permissions")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('USER_MANAGE') or hasRole('ADMIN')")
    @Operation(summary = "Update user permissions", description = "Update permissions for a user (admin only)")
    @ApiResponse(responseCode = "200", description = "User permissions updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<User> updatePermissions(@PathVariable Long id, @RequestBody Set<Permission> permissions) {
        User updated = userService.updatePermissions(id, permissions);
        return ResponseEntity.ok(updated);
    }
}
