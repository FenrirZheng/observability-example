package com.fenrir.example.opentelemetryjava.schedule;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class MetricsPusher {

    private static final Logger log = LoggerFactory.getLogger(MetricsPusher.class);


    @Scheduled(fixedRate = 3000) // 每30秒推送一次
    @Timed(value = "time-metrics-test")
    public void pushMetrics() throws InterruptedException {

        TimeUnit.SECONDS.sleep(new Random().nextInt(5));
        log.info("invoke schedule and metrics...");
    }
}