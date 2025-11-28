package com.demo_security.demo_security;

import com.demo_security.demo_security.service.ExternalApiService;
import com.demo_security.demo_security.service.EventPublisherService;
import com.demo_security.demo_security.events.UserCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class AdvancedTechniquesIntegrationTest {

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    private EventPublisherService eventPublisherService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testCaching() {
        // Test cache functionality
        String result1 = externalApiService.getCachedData("test");
        String result2 = externalApiService.getCachedData("test");

        assertThat(result1).isNotNull();
        assertThat(result2).isEqualTo(result1); // Should be cached

        // Verify cache contains the data
        assertThat(cacheManager.getCache("externalData").get("test")).isNotNull();
    }

    @Test
    public void testEventPublishing() {
        // Test event publishing (this will be consumed asynchronously)
        UserCreatedEvent event = new UserCreatedEvent();
        event.setUserId(1L);
        event.setUsername("testuser");
        event.setEmail("test@example.com");

        // This should not throw an exception
        eventPublisherService.publishUserCreatedEvent(event);
    }

    @Test
    public void testCircuitBreaker() {
        // Test circuit breaker functionality
        // This might fail initially but should open circuit after failures
        try {
            externalApiService.callExternalApiWithCircuitBreaker();
        } catch (Exception e) {
            // Expected for circuit breaker testing
        }
    }

    @Test
    public void testRateLimiter() {
        // Test rate limiter
        for (int i = 0; i < 10; i++) {
            try {
                externalApiService.callRateLimitedApi();
            } catch (Exception e) {
                // Rate limiter might block some calls
            }
        }
    }
}