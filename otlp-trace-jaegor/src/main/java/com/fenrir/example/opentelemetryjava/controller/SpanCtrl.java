package com.fenrir.example.opentelemetryjava.controller;

import com.fenrir.example.opentelemetryjava.service.SpanHappyService;
import io.micrometer.common.KeyValues;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.annotation.SpanTag;
import io.opentelemetry.api.trace.Span;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpanCtrl {


    private final ObservationRegistry observationRegistry;

    private final SpanHappyService spanHappyService;

    public SpanCtrl(ObservationRegistry observationRegistry, SpanHappyService spanHappyService) {
        this.observationRegistry = observationRegistry;
        this.spanHappyService = spanHappyService;
    }

    // https://docs.micrometer.io/tracing/reference/api
    @GetMapping("/span-test/{name}")
    public String get(@PathVariable(value = "name") String name) {
        Observation observation = Observation.createNotStarted("my-operation-span", observationRegistry)
                // lowCardinalityKeyValue只能在start 前設置...
                .lowCardinalityKeyValue("span-test", "span-test")
                .start();
        try {
            if (name.equals("1")) {
                // 需要注意這是高基數還是低基數的數值..., 會影響到prometheus的效能...
                //無效
                observation.lowCardinalityKeyValues(KeyValues.of("name", "1"));
            } else if (name.equals("2")) {
                putV2();
            } else {
                throw new IllegalArgumentException("Invalid name");
            }
            // 執行操作
        } catch (Exception e) {
            observation.error(e);
            throw e;
        } finally {
            observation.stop();
        }
        return "Hello World" + name;
    }

    private void putV2() {
        var c = observationRegistry.getCurrentObservation();
        if (c != null) {
            //無效
            c.lowCardinalityKeyValue("name", "2");
        }
    }


    // 額外的端點示例，展示不同的 tracing 方式
    @GetMapping("/span-annotation/{id}")
    public String getWithAnnotation(@PathVariable @SpanTag("user.id") String id) {
        var currentSpan = Span.current();
        currentSpan.setAttribute("user_id", id);
        return spanHappyService.processWithAnnotation(id);
    }

}
