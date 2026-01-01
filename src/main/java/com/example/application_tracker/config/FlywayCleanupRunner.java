package com.example.application_tracker.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FlywayCleanupRunner implements CommandLineRunner {

    private final Flyway flyway;

    public FlywayCleanupRunner(Flyway flyway) {
        this.flyway = flyway;
    }

    @Override
    public void run(String... args) {
        flyway.clean();  // Force clean here
        flyway.migrate();  // Then run migrations
    }
}