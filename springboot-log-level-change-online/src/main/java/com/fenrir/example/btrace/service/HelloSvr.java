package com.fenrir.example.btrace.service;

import io.micrometer.tracing.annotation.NewSpan;

public interface HelloSvr {
    @NewSpan("newSpan2")
    String newSpan2(String id);
}
