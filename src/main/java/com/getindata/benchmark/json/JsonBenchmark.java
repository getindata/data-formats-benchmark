package com.getindata.benchmark.json;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JsonBenchmark {

    private static final JsonConverter JSON_CONVERTER = new JsonConverter();

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        public String[] inputRecords;

        @Setup
        public void doSetup() throws IOException {
            inputRecords = Files.readString(Path.of("test-data/test_data.json")).split("\n");
        }
    }

    @Benchmark
    public void readJsonObjects(JsonBenchmark.BenchmarkState state, Blackhole blackhole) throws Exception {
        blackhole.consume(JSON_CONVERTER.convert(state.inputRecords));
    }

}
