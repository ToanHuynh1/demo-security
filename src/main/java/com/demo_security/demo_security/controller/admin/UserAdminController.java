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

@RestController
@RequestMapping("/api/admin/users")
public class UserAdminController {
    @Autowired
    private UserService userService;


    @GetMapping
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.update(id, user);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<User> updatePermissions(@PathVariable Long id, @RequestBody Set<Permission> permissions) {
        User updated = userService.updatePermissions(id, permissions);
        return ResponseEntity.ok(updated);
    }
}
