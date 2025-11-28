package com.demo_security.demo_security.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiService.class);
    private final RestTemplate restTemplate;

    public ExternalApiService() {
        this.restTemplate = new RestTemplate();
    }

    @Cacheable(value = "apiResponse", key = "#url")
    @CircuitBreaker(name = "externalService", fallbackMethod = "fallbackResponse")
    @Retry(name = "externalService")
    @RateLimiter(name = "apiRateLimit")
    @Timed(value = "external.api.call", description = "Time taken to call external API")
    public String callExternalApi(String url) {
        logger.info("Calling external API: {}", url);
        try {
            // Simulate API call
            Thread.sleep(100); // Simulate network delay
            return "Response from " + url;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("API call interrupted", e);
        }
    }

    public String fallbackResponse(String url, Throwable throwable) {
        logger.warn("Circuit breaker activated for URL: {}, Error: {}", url, throwable.getMessage());
        return "Fallback response for " + url;
    }

    @Cacheable(value = "externalData", key = "#data")
    public String getCachedData(String data) {
        logger.info("Fetching cached data for: {}", data);
        // Simulate data retrieval
        return "Cached data for: " + data;
    }

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallbackResponse")
    @Retry(name = "externalService")
    public String callExternalApiWithCircuitBreaker() {
        return callExternalApi("http://example.com/api/test");
    }

    @RateLimiter(name = "apiRateLimit")
    public String callRateLimitedApi() {
        logger.info("Calling rate limited API");
        try {
            Thread.sleep(50); // Simulate processing
            return "Rate limited response";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Rate limited call interrupted", e);
        }
    }
}