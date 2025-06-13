## Micrometer 與分散式追蹤 (Distributed Tracing)

Micrometer 是一個針對 JVM 應用程式的度量 (metrics) 檢測庫，它像一個外觀 (facade) 層，統一了各種監控系統的 API。這讓你可以收集應用程式的度量數據，而不需要綁定到特定的後端監控系統。Micrometer Tracing 則將這個概念延伸到分散式追蹤 (distributed tracing)，為不同的追蹤庫 (如 Brave 和 OpenTelemetry) 提供了類似的外觀層。

### 核心概念

* **度量 (Metrics) 與追蹤 (Tracing)**
    * **度量** 用於追蹤效能和使用情況。
    * **追蹤** 則有助於理解請求在不同服務間的流動。
    * Micrometer 提供了這兩者的抽象。

* **Span**
    * **Span** 代表追蹤 (trace) 中的單一操作。
    * 它們追蹤時間資訊，並在追蹤中形成樹狀結構，視覺化請求的路徑。

* **Micrometer Observation**
    * Micrometer 的 **Observation API** 允許你創建「觀察 (observations)」，這些觀察可以同時產生度量和追蹤數據。
    * 當一個觀察停止時，它會生成一個計時器 (timer) 度量和一個 Span。

* **追蹤抽象化 (Tracing Abstraction)**
    * Micrometer Tracing 抽象化了底層的追蹤庫，讓你可以輕鬆地在不同的追蹤器 (例如 Brave、OpenTelemetry) 之間切換，只需最少的程式碼更改。

* **自動檢測 (Automatic Instrumentation)**
    * Spring Boot Actuator 會自動配置 Micrometer 和 Micrometer Tracing，為許多 Spring 元件提供自動檢測功能。

* **Span 導出 (Span Export)**
    * Span 可以導出到各種後端系統進行分析，例如 Zipkin 或 Wavefront。

### Span 的創建方式

Span 的創建主要有以下幾種方式：

* **透過 Observation API：** 當一個觀察 (observation) 啟動和停止時，就會創建 Span。
* **透過 `Tracer` API：** Micrometer Tracing 提供了 `Tracer` API，當你需要時，可以直接創建 Span。
* **透過 `ObservationRegistry` 和 `ObservationHandler`：**
    * Spring Boot 會自動配置一個 `ObservationRegistry`。
    * `ObservationRegistry` 用於註冊 `ObservationHandler` 物件。
    * 這些 `ObservationHandler` 會對觀察的生命週期事件 (啟動、停止、錯誤) 做出反應，並可以創建計時器、Span 和日誌。



# micro-meter Observation

以下是一個使用 `Observation` 包裹操作的 Java 範例：

```java
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

public class MyService {

    private final ObservationRegistry observationRegistry;

    public MyService(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    public void myOperation() {
        Observation observation = Observation.createNotStarted("my-operation", observationRegistry)
                .lowCardinalityKeyValue("some-tag", "some-value")
                .start();
        try {
            // 執行操作
        } catch (Exception e) {
            observation.error(e);
            throw e;
        } finally {
            observation.stop();
        }
    }
}
```

在這個範例中，`myOperation` 方法被一個 `Observation` 包裹。當這個觀察停止時，就會創建一個計時器度量和一個 Span。

---

總之，Micrometer 和 Micrometer Tracing 提供了一種強大的方式，可以用度量和追蹤功能來檢測你的 Spring 應用程式。`Observation` API 讓你能夠同時創建度量和 Span，而 Micrometer Tracing 則抽象化了底層的追蹤庫，使得在不同追蹤後端之間切換變得非常容易。