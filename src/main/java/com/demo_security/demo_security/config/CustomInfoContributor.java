package com.demo_security.demo_security.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> customInfo = new HashMap<>();
        customInfo.put("application", "Demo Security");
        customInfo.put("description", "Spring Boot application with security features");
        customInfo.put("features", new String[]{"JWT Authentication", "User Management", "File Upload", "Caching"});
        customInfo.put("startup_time", LocalDateTime.now().toString());

        builder.withDetail("custom", customInfo);
    }
}