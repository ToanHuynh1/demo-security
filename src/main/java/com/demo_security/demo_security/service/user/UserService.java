package com.demo_security.demo_security.service.user;

import com.demo_security.demo_security.payload.user.UserSearchCriteria;
import com.demo_security.demo_security.model.UserDto;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.demo_security.demo_security.model.UserDto;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.demo_security.demo_security.service.common.GenericSearchService;
import org.springframework.data.domain.Sort;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<UserDto> searchUsers(UserSearchCriteria criteria, int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<User> spec = Specification.where(null);
        if (criteria.getUsername() != null && !criteria.getUsername().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("username"), "%" + criteria.getUsername() + "%"));
        }
        if (criteria.getRole() != null && !criteria.getRole().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("role"), criteria.getRole()));
        }
        return GenericSearchService.search(userRepository, spec, pageable, UserDto::fromEntity);
    }


    @Cacheable("usersByUsername")
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @CacheEvict(value = "allUsers", allEntries = true)
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

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Cacheable("usersById")
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @CacheEvict(value = "allUsers", allEntries = true)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @CacheEvict(value = "allUsers", allEntries = true)
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
