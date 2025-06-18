package com.fenrir.example.btrace.service;

import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.ContinueSpan;
import io.micrometer.tracing.annotation.NewSpan;
import io.micrometer.tracing.annotation.SpanTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpanHappyServiceImpl implements SpanHappyService {

    private static final Logger log = LoggerFactory.getLogger(SpanHappyServiceImpl.class);

    private final Tracer tracer;

    private final HelloSvr helloSvr;

    public SpanHappyServiceImpl(Tracer tracer, HelloSvr helloSvr) {
        this.tracer = tracer;
        this.helloSvr = helloSvr;
    }

    @NewSpan("service.process-with-annotation")
    // 這是用來加log　event的註解
    @ContinueSpan(log = "hello babe!")
    // annotation　只能　塞入　lowCardinalityKeyValues, 動態變數不支援...
    public String processWithAnnotation(@SpanTag("input.id") String id) {
        var t = tracer.currentSpan();
        if (t != null) {
            t.tag("add.tag.by.manual", id);
        }

        return newSpan2(id);
    }

    public String newSpan2(String id) {
        return helloSvr.newSpan2(id);
    }

}
