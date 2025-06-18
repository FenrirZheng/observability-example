package com.fenrir.example.btrace;

import org.openjdk.btrace.core.annotations.*;
import org.openjdk.btrace.core.types.AnyType;

import java.util.concurrent.atomic.AtomicInteger;

import static org.openjdk.btrace.core.BTraceUtils.*;


@BTrace(trusted = true)
public class DeepTraceControllerMethod {

    final static String ENTRY_CLASS = "com.fenrir.example.btrace.controller.SpanCtrl";

    final static String ENTRY_METHOD = "getWithAnnotation2";

    final static String TRACE_RANGE = "/com.fenrir.example.btrace.*/";

    final static boolean ignoreSpringCLI = true;


    // 使用 @TLS (Thread-Local Storage) 作為追蹤開關和深度計數器
    @TLS
    private static final AtomicInteger depth = new AtomicInteger();
    private static String currentThread;

    // =================================================================================
    // 自訂的、BTrace安全的縮排產生函數 (替代 Strings.padding)
    // =================================================================================
    public static String createIndent(int level) {
        // 在 BTrace 中，避免使用迴圈。這裡用簡單的遞迴來安全地產生縮排。
        if (level <= 0) {
            return "";
        }
        // 每次遞迴添加四個空格
        return Strings.strcat("@", createIndent(level - 1));
    }


    // =================================================================================
    // 探測點 1: 追蹤的起點 (當 getWithAnnotation2 被呼叫時)
    // =================================================================================
    @OnMethod(
            // !!! 請將這裡換成您 Controller 的完整類別名稱 !!!
            clazz = ENTRY_CLASS,
            method = ENTRY_METHOD,
            location = @Location(Kind.ENTRY)
    )
    public static void onStartTrace(
            @Self AnyType self,
            @ProbeClassName String className,
            @ProbeMethodName String method,
            AnyType[] args) {
        String name = Thread.currentThread().getName();
        println("==================== DEEP TRACE STARTED ==================== on thread: [" + name + "]");
        DeepTraceControllerMethod.currentThread = name;
        println(Strings.strcat("Triggered by: SpanCtrl.getWithAnnotation2() with args: ", str(args[0])));
        depth.set(0);
    }

    // =================================================================================
    // 探測點 2: 通用方法進入追蹤器
    // =================================================================================
    @OnMethod(
            clazz = TRACE_RANGE, // <-- 建議縮小範圍以提高性能
            method = "/.*/",
            location = @Location(Kind.ENTRY)
    )
    public static void onMethodEntry(@ProbeClassName String pcn, @ProbeMethodName String pmn, AnyType[] args) {
        int currentDepth = depth.get();

        String name = Thread.currentThread().getName();

        if (currentThread == null || !currentThread.equals(name)) {
            return;
        }

        if (Strings.startsWith(pcn, "org.openjdk.btrace.")) {
            return;
        }

        if (ignoreSpringCLI && pcn.contains("$$SpringCGLIB")) {
            return;
        }

        // 使用我們自訂的 createIndent 函數
        String indent = createIndent(currentDepth);

        println("############# call method ##########################");
        print(Strings.strcat(indent, "-> "));
        print(Strings.strcat(pcn, "#") + pmn);
        println("----------------------- all args -----------------------");
        print("(");
        printArray(args);
        println("-----------------------");
        println("detail of args:");

        // COPY-IT-[1]
        for (AnyType arg : args) {
            if (classOf(arg) == String.class) {
                println(arg);
            } else {
                print(classOf(arg).getName());
                printFields(arg);
            }
        }
        // COPY-IT-[1]
        println(")");

        depth.set(currentDepth + 1);
    }


    // =================================================================================
    // 探測點 3: 通用方法返回追蹤器
    // =================================================================================
    @OnMethod(
            clazz = TRACE_RANGE, // <-- 建議縮小範圍以提高性能
            method = "/.*/",
            location = @Location(Kind.RETURN)
    )
    public static void onMethodReturn(@ProbeClassName String pcn, @ProbeMethodName String pmn, @Return AnyType result) {
        int currentDepth = depth.get();
        if (currentDepth == 0) {
            return;
        }

        String name = Thread.currentThread().getName();
        if (currentThread == null || !currentThread.equals(name)) {
            return;
        }

        if (Strings.startsWith(pcn, "org.openjdk.btrace.")) {
            return;
        }

        if (ignoreSpringCLI && pcn.contains("$$SpringCGLIB")) {
            return;
        }

        int newDepth = currentDepth - 1;
        depth.set(newDepth);

        // 使用我們自訂的 createIndent 函數
        String indent = createIndent(newDepth);

        print(Strings.strcat(indent, "<- "));
        print(Strings.strcat(pcn, "#") + pmn);
        println("returns {");
        AnyType arg = result;
        // COPY-IT-[1]
        if (classOf(arg) == String.class) {
            println(arg);
        } else {
            print(classOf(arg).getName());
            printFields(arg);
        }
        // COPY-IT-[1]
        println("}");
        print("---------------------------- method returned---------------------------");
    }


    // =================================================================================
    // 探測點 4: 追蹤的終點 (當 getWithAnnotation2 返回時)
    // =================================================================================
    @OnMethod(
            // !!! 請將這裡換成您 Controller 的完整類別名稱 !!!
            clazz = ENTRY_CLASS,
            method = ENTRY_METHOD,
            location = @Location(Kind.RETURN)
    )
    public static void onEndTrace(@Return AnyType result) {
        if (depth.get() != 0) {
            println(Strings.strcat("SpanCtrl.getWithAnnotation2() Final Return: ", str(result)));
            println("==================== DEEP TRACE ENDED ======================\n");
            depth.set(0);
        }
    }
}