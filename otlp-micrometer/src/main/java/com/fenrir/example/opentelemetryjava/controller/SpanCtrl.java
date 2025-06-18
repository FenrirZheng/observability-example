package com.fenrir.example.opentelemetryjava.controller;

import com.fenrir.example.opentelemetryjava.service.SpanHappyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpanCtrl {


    private final SpanHappyService spanHappyService;

    public SpanCtrl(SpanHappyService spanHappyService) {
        this.spanHappyService = spanHappyService;
    }

    @GetMapping("/span-test/{id}")
    public String getId(@PathVariable String id) {
        return "Hello World!" + id;
    }


    // 額外的端點示例，展示不同的 tracing 方式
    @GetMapping("/span-annotation-2/{id}")
    public String getWithAnnotation2(@PathVariable String id) {
        return spanHappyService.processWithAnnotation(id);
    }

}
