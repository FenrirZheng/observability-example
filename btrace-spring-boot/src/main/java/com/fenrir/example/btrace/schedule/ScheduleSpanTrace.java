package com.fenrir.example.btrace.schedule;

import io.micrometer.tracing.annotation.NewSpan;
//import io.opentelemetry.api.trace.Span;
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
    @NewSpan("scheduleSpan")
    private void doRunnable() {
    }
}
