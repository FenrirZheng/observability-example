# all in one Jaeger and config by yaml is for Jaegor 2.0

- [config-bager.yaml](config-bager.yaml)
    - copy from https://github.com/jaegertracing/jaeger/blob/v2.7.0/cmd/jaeger/config-badger.yaml
    - remove this

```yaml
#    ui:
#      config_file: ./cmd/jaeger/config-ui.json
```


## 只能用環境變數配置badger storage

* see https://www.jaegertracing.io/docs/1.19/deployment/