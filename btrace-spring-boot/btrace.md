# btrace

# 安裝

用sdkman安裝就可以了

# 執行方式

```shell
➜  btrace git:(main) ✗ ps -ef | grep example
fenrir    118333  115633 20 11:34 pts/2    00:00:16 java -jar btrace-example-0.0.1-SNAPSHOT.jar
fenrir    120071  106550  0 11:35 pts/1    00:00:00 grep --color=auto --exclude-dir=.bzr --exclude-dir=CVS --exclude-dir=.git --exclude-dir=.hg --exclude-dir=.svn --exclude-dir=.idea --exclude-dir=.tox --exclude-dir=.venv --exclude-dir=venv example
```

## 監督目標方法被調用時的指定範圍內的call stack

- 包含input
- 包含output
- see [DeepTraceControllerMethod.java](src/test/java/com/fenrir/example/btrace/DeepTraceControllerMethod.java)

# btrace和spring 調用的神奇招數

## 準備動作

* [spring_at_classpath.md](doc/spring_at_classpath.md)

## 使用方式

-[SpringTrace.java](src/test/java/com/fenrir/example/btrace/SpringTrace.java)

---

# 預測情況

## 不能用迴圈

您好，您遇到的這個錯誤 "for loops are not allowed" 是 BTrace 中一個非常核心且重要的設計原則，所有 BTrace
新手都會遇到。這不是您的語法錯誤，而是 BTrace 為了保證安全而故意設下的限制。

為什麼 BTrace 禁止 For 迴圈？
BTrace 的首要設計目標是安全。它被設計用來在生產環境 (Production Environment) 中動態追蹤正在運行的 Java
應用，因此它必須保證追蹤腳本本身不會對目標應用程式造成負面影響，例如：

性能耗盡：一個無邊界的 for 或 while 迴圈可能會陷入長時間的計算，佔用大量 CPU，導致目標應用程式的執行緒被長時間卡住，甚至拖垮整個服務。
不可預測的執行時間：BTrace 要求注入的追蹤程式碼必須在極短且可預測的時間內完成。迴圈的執行時間是動態的，違反了這個原則。
為了達成這個安全目標，BTrace 的驗證器 (Verifier) 會在編譯腳本時，禁止以下幾種語法：

任何形式的迴圈 (for, while, do-while)。
建立新物件 (new 關鍵字)。
建立陣列。
拋出或捕捉例外 (throw, catch)。
同步 (synchronized 關鍵字)。
呼叫任意的外部方法 (只能呼叫 BTraceUtils 和其他一些白名單中的方法)。
如何解決：BTrace 的處理方式
既然不能用迴圈，那該如何處理陣列或集合呢？BTrace 提倡使用 "迴圈展開 (Loop Unrolling)" 的方式來處理。

也就是說，你需要手動地、一個一個地存取陣列中的元素，並為你預期可能的最大陣列長度編寫程式碼。

## 透過annotation啟用

```java
@BTrace(trusted = true)

```