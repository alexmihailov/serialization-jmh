# Сравнение сериализаторов Lagom

## Среда
* Windows Windows 10 Pro (19042.1165)
* 11th Gen Intel(R) Core(TM) i7-1165G7 @ 2.80GHz   2.80 GHz
* JMH version: 1.35
* VM version: JDK 11.0.11, OpenJDK 64-Bit Server VM, 11.0.11+9-LTS

## Операций в секунду

```
Benchmark                                   Mode  Cnt      Score      Error  Units
SerializationBenchmark.jaksonDeserialize   thrpt    9  56553.725 ?  897.325  ops/s
SerializationBenchmark.jaksonSerialize     thrpt    9    427.926 ?   49.024  ops/s
SerializationBenchmark.kotlinxDeserialize  thrpt    9  84270.402 ? 2707.450  ops/s
SerializationBenchmark.kotlinxSerialize    thrpt    9    242.312 ?    9.844  ops/s
```

## Среднее время на одну операцию (в милисекундах)

```
Benchmark                                  Mode  Cnt  Score    Error  Units
SerializationBenchmark.jaksonDeserialize   avgt    9  0.021 ?  0.004  ms/op
SerializationBenchmark.jaksonSerialize     avgt    9  2.334 ?  0.429  ms/op
SerializationBenchmark.kotlinxDeserialize  avgt    9  0.012 ?  0.001  ms/op
SerializationBenchmark.kotlinxSerialize    avgt    9  4.068 ?  0.138  ms/op
```