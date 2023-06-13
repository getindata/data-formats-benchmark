package com.getindata.benchmark.json;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JsonBenchmark {

    private static final JsonConverter JSON_CONVERTER = new JsonConverter();

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        public List<byte[]> inputRecords;

        @Setup
        public void doSetup() throws IOException {
            inputRecords = Arrays.stream(Files.readString(Path.of("test-data/test_data.json")).split("\n"))
                    .map(String::getBytes)
                    .collect(Collectors.toList());
        }
    }

    @Benchmark
    public void readJsonObjects(JsonBenchmark.BenchmarkState state, Blackhole blackhole) throws Exception {
        blackhole.consume(JSON_CONVERTER.convert(state.inputRecords));
    }

}
