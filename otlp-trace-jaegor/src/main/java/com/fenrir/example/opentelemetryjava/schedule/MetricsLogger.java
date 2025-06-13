package com.fenrir.example.opentelemetryjava.schedule;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;

///
//@Component
public class MetricsLogger {

    private static final Logger logger = LoggerFactory.getLogger(MetricsLogger.class);
    private final MeterRegistry meterRegistry;

    public MetricsLogger(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(fixedRate = 3000) // 每30秒執行一次
    public void logMetrics() {
        meterRegistry.getMeters().forEach(meter -> {
            logger.info("Meter: {} - {}", meter.getId(), meter.measure());
        });
    }


}