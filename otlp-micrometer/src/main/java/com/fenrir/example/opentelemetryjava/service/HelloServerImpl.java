package com.fenrir.example.opentelemetryjava.service;

import io.micrometer.tracing.annotation.NewSpan;
import io.micrometer.tracing.annotation.SpanTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HelloServerImpl implements HelloSvr {

    private static final Logger log = LoggerFactory.getLogger(HelloServerImpl.class);

    private final RestTemplate restTemplate;

    public HelloServerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @NewSpan("call.elf.by.rest.template.xxx")
    @Override
    public String newSpan2(@SpanTag("id.xx") String id) {
        try {
            // 自己call 自己....
            var c = restTemplate.getForObject("http://localhost:8091/span-test/" + id, String.class);
            log.info("newSpan2: {}", c);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }

}
