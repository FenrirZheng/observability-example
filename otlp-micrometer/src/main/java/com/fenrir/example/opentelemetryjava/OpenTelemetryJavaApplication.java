package com.fenrir.example.opentelemetryjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.tracing.BraveAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = {
        // tag使用micro meter　使用這個configuration
        BraveAutoConfiguration.class,  // Exclude Brave
        // OR
//        OpenTelemetryTracingAutoConfiguration.class  // Exclude OpenTelemetry
})
public class OpenTelemetryJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenTelemetryJavaApplication.class, args);
    }

}
