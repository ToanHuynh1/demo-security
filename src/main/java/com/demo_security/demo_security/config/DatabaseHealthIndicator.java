package com.demo_security.demo_security.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {
        try {
            // Kiểm tra kết nối database bằng cách đếm số user
            Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
            return Health.up()
                    .withDetail("database", "MySQL")
                    .withDetail("user_count", userCount)
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}