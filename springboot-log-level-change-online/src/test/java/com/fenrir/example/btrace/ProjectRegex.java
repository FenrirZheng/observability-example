package com.fenrir.example.btrace;// HttpRequestTracer.java

//import com.fenrir.example.btrace.util.BtraceBase;

import org.openjdk.btrace.core.annotations.*;
import org.openjdk.btrace.core.types.AnyType;

import static org.openjdk.btrace.core.BTraceUtils.print;
import static org.openjdk.btrace.core.BTraceUtils.println;

@BTrace(trusted = true)
public class ProjectRegex {


    @OnMethod(
            clazz = "/com.fenrir.example.btrace.*/",
            method = "/.*/",
            location = @Location(
                    value = Kind.RETURN) //在目標方法內部呼叫其他方法時
    )
    public static void doSomeThingReturn(@Self AnyType self,
                                         @ProbeClassName String className,
                                         @ProbeMethodName String method,
                                         // method參數　由此進入,
                                         @Return AnyType returnValue,
                                         AnyType[] request) {
        print(className + "#" + method + "return with => ");
        println(returnValue);
    }
}