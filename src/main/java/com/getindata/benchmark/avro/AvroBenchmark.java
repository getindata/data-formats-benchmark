package com.getindata.benchmark.avro;

import com.getindata.benchmark.compression.Compressor;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.getindata.benchmark.avro.AvroRecords.avroRecordsAsBytes;
import static java.util.stream.Collectors.toList;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class AvroBenchmark {

    private static final AvroDeserializer AVRO_CONVERTER = new AvroDeserializer();

    @Param({"none", "gzip", "lz4", "snappy", "zstd"})
    private String compression;
    private List<byte[]> inputRecords;
    private Compressor compressor;

    @Setup
    public void setup() {
        compressor = Compressor.byName(compression);
        inputRecords = avroRecordsAsBytes().stream().map(compressor::compress).collect(toList());
    }

    @Benchmark
    public void readAvroObjects(Blackhole blackhole) {
        blackhole.consume(
                inputRecords.stream()
                        .map(compressor::decompress)
                        .map(AVRO_CONVERTER::convert)
                        .collect(toList())
        );
    }

}
