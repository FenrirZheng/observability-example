package com.fenrir.example.btrace.controller;

import com.fenrir.example.btrace.controller.dto.HappyDto;
import com.fenrir.example.btrace.service.SpanHappyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class SpanCtrl {

    private static final Logger log = LoggerFactory.getLogger(SpanCtrl.class);


    private final SpanHappyService spanHappyService;

    public SpanCtrl(SpanHappyService spanHappyService) {
        this.spanHappyService = spanHappyService;
    }

    @GetMapping("/span-test/{id}")
    public String getId(@PathVariable String id) {
        return "Hello World!" + id;
    }


    // 額外的端點示例，展示不同的 tracing 方式
    @PostMapping("/span-annotation-2/{id}")
    public String getWithAnnotation2(@RequestBody HappyDto id) {
        var cc = "xx-2" + new Random().nextInt();
        var c = doSomeThing(id, cc);
        log.info("getWithAnnotation2: {}, {}", id, c);
        return spanHappyService.processWithAnnotation(id.getId());
    }

    private String doSomeThing(HappyDto id, String s) {
        log.info("doSomeThing: {}, {}", id, s);
        return "happy" + new Random().nextInt();
    }

}
