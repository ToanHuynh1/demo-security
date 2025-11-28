package com.demo_security.demo_security.controller;

import com.demo_security.demo_security.service.EventPublisherService;
import com.demo_security.demo_security.service.ExternalApiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final ExternalApiService externalApiService;
    private final EventPublisherService eventPublisherService;

    public DemoController(ExternalApiService externalApiService, EventPublisherService eventPublisherService) {
        this.externalApiService = externalApiService;
        this.eventPublisherService = eventPublisherService;
    }

    @GetMapping("/cached/{userId}")
    @Timed(value = "demo.cached.endpoint", description = "Time taken for cached endpoint")
    public ResponseEntity<String> getCachedUserData(@PathVariable Long userId) {
        String data = externalApiService.getCachedData(String.valueOf(userId));
        return ResponseEntity.ok(data);
    }

    @GetMapping("/external-api")
    @CircuitBreaker(name = "externalService", fallbackMethod = "externalApiFallback")
    @RateLimiter(name = "apiRateLimit")
    @Timed(value = "demo.external.api", description = "Time taken for external API call")
    public ResponseEntity<String> callExternalApi(@RequestParam String url) {
        String response = externalApiService.callExternalApi(url);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/events/user-created")
    public ResponseEntity<String> publishUserEvent(@RequestParam Long userId, @RequestParam String username) {
        eventPublisherService.publishUserCreatedEvent(userId, username);
        return ResponseEntity.ok("User created event published");
    }

    @PostMapping("/events/file-uploaded")
    public ResponseEntity<String> publishFileEvent(@RequestParam String fileName, @RequestParam String fileUrl) {
        eventPublisherService.publishFileUploadedEvent(fileName, fileUrl);
        return ResponseEntity.ok("File uploaded event published");
    }

    @PostMapping("/events/email")
    public ResponseEntity<String> publishEmailEvent(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        eventPublisherService.publishEmailNotification(to, subject, body);
        return ResponseEntity.ok("Email notification event published");
    }

    // Circuit breaker fallback method
    public ResponseEntity<String> externalApiFallback(String url, Throwable throwable) {
        return ResponseEntity.ok("Service temporarily unavailable. Please try again later.");
    }
}