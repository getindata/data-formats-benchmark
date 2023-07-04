# data-formats-benchmark

## Running benchmarks locally

Deserialization benchmarks run command:

```
mvn clean package exec:exec@run-benchmark-jar -P run-benchmark
```

Size benchmarks:

```
mvn clean package exec:exec@run-benchmark-jar -P run-size-benchmark
```
