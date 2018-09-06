package org.sample;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        var bench = new MyBenchmark();
        bench.init();
        bench.benchLucene();
        bench.benchJeospatial();
    }
}
