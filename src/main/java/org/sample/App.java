package org.sample;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        var bench = new MyBenchmark();
        bench.initLucene();
//        bench.initJeospatial();
//        bench.initJsi();
        bench.benchLucene();
//        bench.benchJeospatial();
//        bench.benchJsi();
    }
}
