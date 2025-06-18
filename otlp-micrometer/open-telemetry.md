## 看看　Jeager　ui

* 啟動`Jeager`

- [docker-compose.yaml](jaeger/docker-compose.yaml)

* `Jeager` ui 訪問

- http://127.0.0.1:16686/search

## 配置

## 開啟otel collector

* [docker-compose.yaml](otel/docker-compose.yaml)

## 開啟prometheus

* [docker-compose.yaml](prometheus/docker-compose.yaml)

## 開起 `Jaegor`

* [docker-compose.yaml](jaeger/docker-compose.yaml)

### AOP 配置

*[AopMetric.java](src/main/java/com/fenrir/example/opentelemetryjava/metric/AopMetric.java)


## http 測試

- [測試](./http-test/test.http)

* `Jaejor ui`
 - http://127.0.0.1:16686/search


* `prometheus` ui
- http://127.0.0.1:9090/query