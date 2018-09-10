Benchmarks of Geospatial in-memory databases/indices.

The benchmarks are done with [JMH](http://openjdk.java.net/projects/code-tools/jmh/).

Lucene spatial extras https://lucene.apache.org/core/7_4_0/spatial-extras/index.html.

Jeospatial https://jchambers.github.io/jeospatial

Run:

```bash
mvn clean install
java -jar target/benchmarks.jar
# JMH version: 1.21
# VM version: JDK 10, Java HotSpot(TM) 64-Bit Server VM, 10+46
# VM invoker: /Library/Java/JavaVirtualMachines/jdk-10.jdk/Contents/Home/bin/java
# VM options: <none>
# Warmup: <none>
# Measurement: 1 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: org.sample.MyBenchmark.benchJeospatial

# Run progress: 0,00% complete, ETA 00:00:20
# Fork: 1 of 1
Iteration   1: 80134,425 ops/s


Result "org.sample.MyBenchmark.benchJeospatial":
  80134,425 ops/s


# JMH version: 1.21
# VM version: JDK 10, Java HotSpot(TM) 64-Bit Server VM, 10+46
# VM invoker: /Library/Java/JavaVirtualMachines/jdk-10.jdk/Contents/Home/bin/java
# VM options: <none>
# Warmup: <none>
# Measurement: 1 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: org.sample.MyBenchmark.benchLucene

# Run progress: 50,00% complete, ETA 00:00:11
# Fork: 1 of 1
Iteration   1: 1231,907 ops/s


Result "org.sample.MyBenchmark.benchLucene":
  1231,907 ops/s


# Run complete. Total time: 00:00:23

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                     Mode  Cnt      Score   Error  Units
MyBenchmark.benchJeospatial  thrpt       80134,425          ops/s
MyBenchmark.benchLucene      thrpt        1231,907          ops/s
```
