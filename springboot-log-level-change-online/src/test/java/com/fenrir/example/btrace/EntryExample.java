package com.fenrir.example.btrace;// HttpRequestTracer.java

import org.openjdk.btrace.core.annotations.*;
import org.openjdk.btrace.core.types.AnyType;

import java.lang.reflect.Field;

import static org.openjdk.btrace.core.BTraceUtils.Reflective.field;
import static org.openjdk.btrace.core.BTraceUtils.Strings.str;
import static org.openjdk.btrace.core.BTraceUtils.Strings.strcat;
import static org.openjdk.btrace.core.BTraceUtils.*;

@BTrace
public class EntryExample {

    @OnMethod(
            clazz = "com.fenrir.example.btrace.controller.SpanCtrl",
            method = "getWithAnnotation2",
            location = @Location(Kind.ENTRY)
    )
    public static void onHttpRequest(@Self Object self,
                                     @ProbeClassName String className,
                                     @ProbeMethodName String method,
                                     // method參數　由此進入
                                     AnyType[] request) {

        println(strcat("HTTP Request started at: ", str(timeMillis())));
        printArray(request);
        if (request.length > 0) {
            AnyType v = request[0];
            //　取得　com.fenrir.example.btrace.controller.dto.HappyDto　內資訊
            Field id = field(classOf(v), "id"); // private field
            Field a = field(classOf(v), "a");     // public field
            println(strcat("  - id is: ", str(get(id, v))));
            println(strcat("  - a is : ", str(get(a, v))));
        }
    }

    @OnMethod(
            clazz = "org.springframework.web.servlet.DispatcherServlet",
            method = "doDispatch",
            location = @Location(Kind.RETURN)
    )
    public static void onHttpRequestReturn(@Self Object self, @Duration long duration) {
        println(strcat("HTTP Request completed in: ", str(duration / 1000000)));
        println("------------------------");
    }
}