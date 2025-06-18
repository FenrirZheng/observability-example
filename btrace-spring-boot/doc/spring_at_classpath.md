將dependencies會出到目標classpath目錄, 使用gradle為例

```gradle
  
// 建立一個名為 exportDependencies 的任務，其類型為 Copytasks.register('exportDependencies', Copy) {  
    // 描述：這個任務會將執行時期的依賴項複製到 build/exported-libs 目錄下  
    description = 'Exports runtime dependencies to a specific folder.'  
    // 設定來源：從 runtimeClasspath 這個設定中取得所有依賴項檔案  
    // runtimeClasspath 包含了執行時期需要的所有依賴項 (implementation 和 runtimeOnly)    from configurations.runtimeClasspath  
  
    // 設定目標目錄  
    // layout.buildDirectory.dir('exported-libs') 會指向 build/exported-libs    into layout.buildDirectory.dir('exported-libs')  
}
```

自己加`CLASSPATH`環境變數到java指令中

找出 #btrace 的執行shell

```
vi /home/fenrir/.sdkman/candidates/btrace/2.2.6/bin/btrace
```

改啟動的java

```shell
${JAVA_HOME}/bin/java ${JAVA_ARGS} -cp ${BTRACE_HOME}/libs/btrace-client.jar:${TOOLS_JAR}:/usr/share/lib/java/dtrace.jar:${CLASSPATH} org.openjdk.btrace.client.Main $*

```

快速加class path的到環境變數的方法, java需要把每個jar寫清楚標記載`-cp`內

```
export CLASSPATH="$(echo /home/fenrir/code/my-github/observability-example/btrace-spring-boot/build/exported-libs/*.jar | tr ' ' ':')"    

```

如果使用spring boot 把plan jar放入class path

```
![](img0/_0_tmp/btrace-example-0.0.1-SNAPSHOT-plain.jar)
```