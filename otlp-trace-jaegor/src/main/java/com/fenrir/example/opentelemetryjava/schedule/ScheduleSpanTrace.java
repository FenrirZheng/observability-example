package com.fenrir.example.opentelemetryjava.schedule;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ScheduleSpanTrace implements InitializingBean {


    @Override
    public void afterPropertiesSet() {
        Thread.ofPlatform().name("test-span").start(this::doRunnable);
    }

    /**
     * 在thread上發起operation, + span
     */
    @WithSpan("scheduleSpan")
    private void doRunnable() {
        Span.current().setAttribute("schedule-span", "true");
    }
}
