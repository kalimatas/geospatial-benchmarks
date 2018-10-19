### Overview

Benchmarks of Geospatial in-memory databases/indices.

The benchmarks are done with [JMH](http://openjdk.java.net/projects/code-tools/jmh/).

Lucene spatial extras https://lucene.apache.org/core/7_4_0/spatial-extras/index.html.

Jeospatial https://jchambers.github.io/jeospatial.

Java Spatial Index https://github.com/aled/jsi.

### Results

```bash
mvn clean install
java -jar target/benchmarks.jar -foe true -rf csv -rff benchmark.csv
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.openjdk.jmh.util.Utils (file:/Users/kalimatas/p/java/geospatial-benchmarks/target/benchmarks.jar) to field java.io.Console.cs
WARNING: Please consider reporting this to the maintainers of org.openjdk.jmh.util.Utils
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
# JMH version: 1.21
# VM version: JDK 10, Java HotSpot(TM) 64-Bit Server VM, 10+46
# VM invoker: /Library/Java/JavaVirtualMachines/jdk-10.jdk/Contents/Home/bin/java
# VM options: <none>
# Warmup: <none>
# Measurement: 3 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: org.sample.MyBenchmark.benchJeospatial

# Run progress: 0,00% complete, ETA 00:01:30
# Fork: 1 of 1
Iteration   1: 91466,644 ops/s
Iteration   2: 100407,020 ops/s
Iteration   3: 105833,521 ops/s


Result "org.sample.MyBenchmark.benchJeospatial":
  99235,728 ±(99.9%) 132352,814 ops/s [Average]
  (min, avg, max) = (91466,644, 99235,728, 105833,521), stdev = 7254,704
  CI (99.9%): [≈ 0, 231588,542] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 10, Java HotSpot(TM) 64-Bit Server VM, 10+46
# VM invoker: /Library/Java/JavaVirtualMachines/jdk-10.jdk/Contents/Home/bin/java
# VM options: <none>
# Warmup: <none>
# Measurement: 3 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: org.sample.MyBenchmark.benchJsi

# Run progress: 33,33% complete, ETA 00:01:04
# Fork: 1 of 1
Iteration   1: 297538,462 ops/s
Iteration   2: 319130,307 ops/s
Iteration   3: 312717,617 ops/s


Result "org.sample.MyBenchmark.benchJsi":
  309795,462 ±(99.9%) 202296,631 ops/s [Average]
  (min, avg, max) = (297538,462, 309795,462, 319130,307), stdev = 11088,561
  CI (99.9%): [107498,831, 512092,093] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 10, Java HotSpot(TM) 64-Bit Server VM, 10+46
# VM invoker: /Library/Java/JavaVirtualMachines/jdk-10.jdk/Contents/Home/bin/java
# VM options: <none>
# Warmup: <none>
# Measurement: 3 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: org.sample.MyBenchmark.benchLucene

# Run progress: 66,67% complete, ETA 00:00:31
# Fork: 1 of 1
Iteration   1: 1132,558 ops/s
Iteration   2: 1203,142 ops/s
Iteration   3: 1263,893 ops/s


Result "org.sample.MyBenchmark.benchLucene":
  1199,864 ±(99.9%) 1199,138 ops/s [Average]
  (min, avg, max) = (1132,558, 1199,864, 1263,893), stdev = 65,729
  CI (99.9%): [0,726, 2399,003] (assumes normal distribution)


# Run complete. Total time: 00:01:35

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                     Mode  Cnt       Score        Error  Units
MyBenchmark.benchJeospatial  thrpt    3   99235,728 ± 132352,814  ops/s
MyBenchmark.benchJsi         thrpt    3  309795,462 ± 202296,631  ops/s
MyBenchmark.benchLucene      thrpt    3    1199,864 ±   1199,138  ops/s

Benchmark result is saved to benchmark.csv
```
