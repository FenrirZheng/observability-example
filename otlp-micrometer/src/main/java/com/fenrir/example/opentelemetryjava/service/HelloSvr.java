package com.fenrir.example.opentelemetryjava.service;

import io.micrometer.tracing.annotation.NewSpan;

public interface HelloSvr {
    @NewSpan("newSpan2")
    String newSpan2(String id);
}
