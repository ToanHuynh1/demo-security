package com.demo_security.demo_security.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private Flyway flyway;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Flyway Migration Status ===");
        MigrationInfo current = flyway.info().current();
        if (current != null) {
            System.out.println("Flyway version: " + current.getVersion());
            System.out.println("Migration description: " + current.getDescription());
            System.out.println("Executed at: " + current.getInstalledOn());
        }
        MigrationInfo[] allMigrations = flyway.info().all();
        System.out.println("Total migrations applied: " + allMigrations.length);
        System.out.println("===============================");

        // Có thể thêm logic khởi tạo dữ liệu sau migration ở đây
        // Ví dụ: tạo admin user mặc định, kiểm tra data integrity, etc.
    }
}