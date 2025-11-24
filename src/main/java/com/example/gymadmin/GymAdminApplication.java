package com.example.gymadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GymAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(GymAdminApplication.class, args);
    }
}
