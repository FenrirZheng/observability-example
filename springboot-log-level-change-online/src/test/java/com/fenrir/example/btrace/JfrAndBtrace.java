package com.fenrir.example.btrace;


//import org.openjdk.btrace.core.BTraceUtils;

import org.openjdk.btrace.core.BTraceUtils;
import org.openjdk.btrace.core.annotations.*;
import org.openjdk.btrace.core.jfr.JfrEvent;
import org.openjdk.btrace.core.types.AnyType;

import static org.openjdk.btrace.core.BTraceUtils.*;

/**
 * ｂtrace script內不要寫try catch, 它會不能編譯執行
 */
@BTrace(trusted = true)
public class JfrAndBtrace {

    // 1. 定義 JFR 事件工廠
    @Event(
            name = "eventFactory",
            label = "Method Execution Time",
            description = "Tracks the execution time of a specific method",
            category = {"Application", "Performance"},
            fields = {
                    @Event.Field(type = Event.FieldType.STRING, name = "methodName"),
                    @Event.Field(type = Event.FieldType.STRING, name = "executionTime"),
                    @Event.Field(type = Event.FieldType.STRING, name = "parameter"),
            }

    )
    private static JfrEvent.Factory eventFactory;

    //    @TLS
//    private static JfrEvent event;
//
    @TLS
    private static Long startTime = 0L;

    final static String ENTRY_CLASS = "com.fenrir.example.btrace.controller.SpanCtrl";

    final static String ENTRY_METHOD = "getWithAnnotation2";

    @TLS
    private static StringBuilder logBuilder;
    // 2. 在目標方法進入時觸發

    @OnMethod(
            clazz = ENTRY_CLASS,
            method = ENTRY_METHOD,
            location = @Location(Kind.ENTRY)
    )
    public static void onMethodEntry(
            @Self AnyType self,
            @ProbeClassName String className,
            @ProbeMethodName String method,
            // method參數　由此進入
            AnyType[] request) {
        logBuilder = new StringBuilder();
        logBuilder.append("prepare event: ").append(className).append("#").append(method).append("\n");

        // 準備事件並開始計時
        startTime = BTraceUtils.Time.millis();

        logBuilder.append("prepare event finished: ").append(className).append("#").append(method).append("\n");
    }

//    @OnMethod(
//            clazz = ENTRY_CLASS,
//            method = ENTRY_METHOD,
//            location = @Location(Kind.ERROR)
//    )
//    public static void onMethodError(
//            @Self AnyType self,
//            @ProbeClassName String pcn,
//            @ProbeMethodName String pmn,
//            Throwable throwable
//    ) {
//        BTraceUtils.print("Method threw exception: " + pcn + "#" + pmn + " - " + throwable);
//        // 清理 ThreadLocal 資源
//    }

    // 3. 在目標方法返回時觸發
    @OnMethod(
            clazz = ENTRY_CLASS,
            method = ENTRY_METHOD,
            location = @Location(Kind.RETURN)
    )
    public static void onMethodReturn2(
            @Self AnyType self,
            @ProbeClassName String pcn,
            @ProbeMethodName String pmn,
            @Return AnyType returnType,
            // method參數　由此進入
            AnyType[] input
    ) {

        logBuilder.append("commit event: ").append(pcn).append("#").append(pmn).append("\n");
        logBuilder.append(startTime).append("ms").append("\n");
        JfrEvent jfrEvent = BTraceUtils.Jfr.prepareEvent(eventFactory);
        BTraceUtils.Jfr.begin(jfrEvent);
        String c = strcat("pmn" + pcn + "#" + pmn + " ", str(BTraceUtils.Time.millis()));
        String cc = strcat(c, "ms");

        BTraceUtils.Jfr.setEventField(jfrEvent, "methodName", cc);
        BTraceUtils.Jfr.commit(jfrEvent);
        println(str(logBuilder));
    }
}