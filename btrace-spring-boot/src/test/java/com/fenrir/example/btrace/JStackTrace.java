package com.fenrir.example.btrace;

import org.openjdk.btrace.core.annotations.*;
import org.openjdk.btrace.core.types.AnyType;

import static org.openjdk.btrace.core.BTraceUtils.*;

@BTrace(trusted = true)
public class JStackTrace {


    @OnMethod(
            clazz = "com.fenrir.example.btrace.service.HelloServerImpl",
            method = "newSpan2",
            location = @Location(Kind.ENTRY)
    )
    public static void onHttpRequest(@Self AnyType self,
                                     @ProbeClassName String className,
                                     @ProbeMethodName String method,
                                     // method參數　由此進入
                                     AnyType[] request) {
        print("in newSpan2");
        //jstackStr(); 不給用....
        jstack(50);
        println("------------------------");

    }
}
