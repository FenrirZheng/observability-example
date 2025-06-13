package com.fenrir.example.opentelemetryjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OpenTelemetryJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenTelemetryJavaApplication.class, args);
    }

}
