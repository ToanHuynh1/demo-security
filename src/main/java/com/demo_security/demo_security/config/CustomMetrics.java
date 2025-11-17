package com.demo_security.demo_security.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    private final Counter loginAttempts;
    private final Counter loginSuccess;
    private final Counter loginFailures;

    public CustomMetrics(MeterRegistry meterRegistry) {
        this.loginAttempts = Counter.builder("login_attempts_total")
                .description("Total number of login attempts")
                .register(meterRegistry);

        this.loginSuccess = Counter.builder("login_success_total")
                .description("Total number of successful logins")
                .register(meterRegistry);

        this.loginFailures = Counter.builder("login_failures_total")
                .description("Total number of failed logins")
                .register(meterRegistry);
    }

    public void incrementLoginAttempts() {
        loginAttempts.increment();
    }

    public void incrementLoginSuccess() {
        loginSuccess.increment();
    }

    public void incrementLoginFailures() {
        loginFailures.increment();
    }
}