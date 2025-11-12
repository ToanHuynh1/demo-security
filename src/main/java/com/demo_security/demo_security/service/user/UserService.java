package com.demo_security.demo_security.service.user;

import java.util.List;
import com.demo_security.demo_security.model.Permission;
import java.util.Set;
import com.demo_security.demo_security.model.User;
import com.demo_security.demo_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Cacheable("usersByUsername")
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @org.springframework.cache.annotation.CacheEvict(value = "allUsers", allEntries = true)
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void updateRefreshToken(String username, String refreshToken, Long expiry) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setRefreshToken(refreshToken);
            user.setRefreshTokenExpiry(expiry);
            userRepository.save(user);
        }
    }

    public User updatePermissions(Long userId, Set<Permission> permissions) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPermissions(permissions);
        return userRepository.save(user);
    }

    @Cacheable("allUsers")
    public List<User> findAll() {
        System.out.println("Fetching all users from database");
        return userRepository.findAll();
    }

    @Cacheable("usersById")
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @org.springframework.cache.annotation.CacheEvict(value = "allUsers", allEntries = true)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @org.springframework.cache.annotation.CacheEvict(value = "allUsers", allEntries = true)
    public User update(Long id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setUsername(user.getUsername());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        existing.setRole(user.getRole());
        existing.setPermissions(user.getPermissions());
        return userRepository.save(existing);
    }

}
