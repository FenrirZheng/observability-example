package com.fenrir.example.btrace;// HttpRequestTracer.java

//import com.fenrir.example.btrace.util.BtraceBase;

import org.openjdk.btrace.core.annotations.*;
import org.openjdk.btrace.core.types.AnyType;

import static org.openjdk.btrace.core.BTraceUtils.Reflective.printFields;
import static org.openjdk.btrace.core.BTraceUtils.Strings.strcat;
import static org.openjdk.btrace.core.BTraceUtils.print;
import static org.openjdk.btrace.core.BTraceUtils.println;

@BTrace(trusted = true)
public class Call {

    @OnMethod(
            clazz = "com.fenrir.example.btrace.controller.SpanCtrl",
            method = "getWithAnnotation2",
            location = @Location(
                    value = Kind.CALL,
                    clazz = "/com.fenrir.example.btrace.*/",
                    method = "/.*/") //在目標方法內部呼叫其他方法時
    )
    public static void onMethodCall(@Self Object self,
                                    @ProbeClassName String className,
                                    @ProbeMethodName String method,
                                    @TargetInstance Object targetInstance, // 被呼叫的實例
                                    @TargetMethodOrField String targetMethod, // 被呼叫的方法名,
                                    AnyType[] request) {
        println(" on " + strcat(className, strcat(" call method:~: ", targetMethod)));
        if (request.length >= 1) {
            printFields(request[0]);
        }
        if (request.length >= 2) {
            printFields(request[1]);
        }
        if (request.length >= 3) {
            printFields(request[2]);
        }
    }

    @OnMethod(
            clazz = "com.fenrir.example.btrace.controller.SpanCtrl",
            method = "doSomeThing",
            location = @Location(
                    value = Kind.ENTRY) //在目標方法內部呼叫其他方法時
    )
    public static void doSomeThing(@Self Object self,
                                   @ProbeClassName String className,
                                   @ProbeMethodName String method,
                                   // method參數　由此進入
                                   AnyType[] request) {
        println("do doSomeThing with para:");
        printFields(request[0]);
        println(request[1]);
    }

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