package com.fenrir.example.btrace.util;

import org.openjdk.btrace.core.types.AnyType;

import static org.openjdk.btrace.core.BTraceUtils.Reflective.printFields;
import static org.openjdk.btrace.core.BTraceUtils.*;

public class BtraceBase {


    public static void printArray2(AnyType[] request) {
        printArray(request);
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


    public static void extracted(AnyType[] args) {
        for (int i = 0; i < args.length; i++) {
            AnyType arg = args[i];
            if (classOf(arg) == String.class) {
                println(arg);
            } else {
                print(classOf(arg).getName());
                printFields(args[i]);
            }
        }
    }

}
