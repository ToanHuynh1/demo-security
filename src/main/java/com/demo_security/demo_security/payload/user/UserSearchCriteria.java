package com.demo_security.demo_security.payload.user;

public class UserSearchCriteria {
    private String username;
    private String role;
    // Thêm các trường filter khác nếu cần

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
