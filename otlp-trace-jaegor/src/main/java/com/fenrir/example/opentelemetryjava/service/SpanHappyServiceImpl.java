package com.fenrir.example.opentelemetryjava.service;

import io.micrometer.common.KeyValue;
import io.micrometer.core.aop.MeterTag;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import io.opentelemetry.api.trace.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class SpanHappyServiceImpl implements SpanHappyService {

    private final ObservationRegistry observationRegistry;

    private final ExecutorService executor;

    private static final Logger log = LoggerFactory.getLogger(SpanHappyServiceImpl.class);


    public SpanHappyServiceImpl(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
        executor = Executors.newFixedThreadPool(10, (v) -> Thread.ofPlatform().name("span-thread-", 0).unstarted(v));
    }

    // annotation　只能　塞入　lowCardinalityKeyValues, 動態變數不支援...
    @Observed(name = "xx-observe", lowCardinalityKeyValues = {
            "xid", "low", "xxx-id", "#id"
    })
    public String processWithAnnotation(@MeterTag(key = "xxx-id") String id) {
        return newSpan(id);
    }


    public String newSpan(String id) {
        // 業務邏輯
        Future<?> submit = executor.submit(this::doAsync);
        try {
            submit.get();
            var currentSpan = Span.current();
            currentSpan.setAttribute("before-response", "true");
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
        return "Processed: " + id;
    }

    private void doAsync() {
        var currentSpan = Span.current();
        var tn = Thread.currentThread().getName();
        currentSpan.setAttribute("do-async", tn);

    }
}
