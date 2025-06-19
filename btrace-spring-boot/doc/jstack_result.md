```shell
➜  btrace git:(main) ✗ btrace 100978  JStackTrace.java
OpenJDK 64-Bit Server VM warning: Option AllowRedefinitionToAddDeleteMethods was deprecated in version 13.0 and will likely be removed in a future release.
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/home/fenrir/.sdkman/candidates/btrace/2.2.6/libs/btrace-client.jar!/org/openjdk/btrace/libs/client/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/home/fenrir/code/my-github/observability-example/btrace-spring-boot/build/exported-libs/btrace-client-2.2.6.jar!/org/openjdk/btrace/libs/client/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.openjdk.btrace.libs.client.org.slf4j.impl.SimpleLoggerFactory]
[main] INFO org.openjdk.btrace.client.Main - Attaching BTrace to PID: 100978
[main] INFO org.openjdk.btrace.client.Client - Successfully started BTrace probe: JStackTrace.java
in newSpan2
java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
java.base/java.lang.reflect.Method.invoke(Method.java:580)
org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89)
io.micrometer.tracing.annotation.SpanAspectMethodInvocation.proceed(SpanAspectMethodInvocation.java:47)
io.micrometer.tracing.annotation.ImperativeMethodInvocationProcessor.proceedUnderSynchronousSpan(ImperativeMethodInvocationProcessor.java:91)
io.micrometer.tracing.annotation.ImperativeMethodInvocationProcessor.process(ImperativeMethodInvocationProcessor.java:73)
io.micrometer.tracing.annotation.SpanAspect.newSpanMethod(SpanAspect.java:59)
java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
java.base/java.lang.reflect.Method.invoke(Method.java:580)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:642)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:632)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:71)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:173)
org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:728)
com.fenrir.example.btrace.service.HelloServerImpl$$SpringCGLIB$$0.newSpan2(<generated>)
com.fenrir.example.btrace.service.SpanHappyServiceImpl.newSpan2(SpanHappyServiceImpl.java:39)
com.fenrir.example.btrace.service.SpanHappyServiceImpl.processWithAnnotation(SpanHappyServiceImpl.java:35)
java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
java.base/java.lang.reflect.Method.invoke(Method.java:580)
org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89)
io.micrometer.tracing.annotation.SpanAspectMethodInvocation.proceed(SpanAspectMethodInvocation.java:47)
io.micrometer.tracing.annotation.ImperativeMethodInvocationProcessor.proceedUnderSynchronousSpan(ImperativeMethodInvocationProcessor.java:91)
io.micrometer.tracing.annotation.ImperativeMethodInvocationProcessor.process(ImperativeMethodInvocationProcessor.java:73)
io.micrometer.tracing.annotation.SpanAspect.newSpanMethod(SpanAspect.java:59)
java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
java.base/java.lang.reflect.Method.invoke(Method.java:580)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:642)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:632)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:71)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:173)
org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89)
io.micrometer.tracing.annotation.SpanAspectMethodInvocation.proceed(SpanAspectMethodInvocation.java:47)
io.micrometer.tracing.annotation.ImperativeMethodInvocationProcessor.proceedUnderSynchronousSpan(ImperativeMethodInvocationProcessor.java:91)
io.micrometer.tracing.annotation.ImperativeMethodInvocationProcessor.process(ImperativeMethodInvocationProcessor.java:73)
io.micrometer.tracing.annotation.SpanAspect.continueSpanMethod(SpanAspect.java:51)
java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
java.base/java.lang.reflect.Method.invoke(Method.java:580)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:642)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:632)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:71)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:173)
org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)
56 more frame(s) ...

```
