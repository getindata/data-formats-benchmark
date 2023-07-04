package com.getindata.benchmark;

import com.getindata.benchmark.compression.Compressor;
import lombok.Builder;
import lombok.Value;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.getindata.benchmark.SizeBenchmark.FormatType.AVRO;
import static com.getindata.benchmark.SizeBenchmark.FormatType.JSON;
import static com.getindata.benchmark.SizeBenchmark.FormatType.PROTOBUF;
import static com.getindata.benchmark.avro.AvroRecords.avroRecordsAsBytes;
import static com.getindata.benchmark.json.JsonRecords.jsonRecordsAsBytes;
import static com.getindata.benchmark.proto.ProtoRecords.protoRecordsAsBytes;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.io.FileUtils.byteCountToDisplaySize;

public class SizeBenchmark {

    enum FormatType {
        AVRO, PROTOBUF, JSON
    }


    @Value
    @Builder
    private static class Measurements {
        FormatType formatType;
        long size;
        long gzipSize;
        long lz4Size;
        long snappySize;
        long zstdSize;
    }

    public static void main(String[] args) throws IOException {
        process(List.of(json(), proto(), avro()));
    }

    private static final Compressor gzipCompressor = Compressor.byName("gzip");
    private static final Compressor zstdCompressor = Compressor.byName("lz4");
    private static final Compressor lz4Compressor = Compressor.byName("zstd");
    private static final Compressor snappyCompressor = Compressor.byName("snappy");

    private static void process(List<Measurements> measurements) throws IOException {
        try (FileWriter writer = new FileWriter("size_benchmark.csv")) {
            for (Measurements m : measurements) {
                var gzipCompressionRate = ((double) m.size) / ((double) m.gzipSize);
                var lz4CompressionRate = ((double) m.size) / ((double) m.lz4Size);
                var snappyCompressionRate = ((double) m.size) / ((double) m.snappySize);
                var zstdCompressionRate = ((double) m.size) / ((double) m.zstdSize);

                System.out.printf("[%8s] NONE:   %s (%d bytes), compression ratio: %.2f%n", m.formatType, byteCountToDisplaySize(m.size), m.size, 1.0d);
                System.out.printf("[%8s] GZIP:   %s (%d bytes), compression ratio: %.2f%n", m.formatType, byteCountToDisplaySize(m.gzipSize), m.gzipSize, gzipCompressionRate);
                System.out.printf("[%8s] LZ4:    %s (%d bytes), compression ratio: %.2f%n", m.formatType, byteCountToDisplaySize(m.lz4Size), m.lz4Size, lz4CompressionRate);
                System.out.printf("[%8s] SNAPPY: %s (%d bytes), compression ratio: %.2f%n", m.formatType, byteCountToDisplaySize(m.snappySize), m.snappySize, snappyCompressionRate);
                System.out.printf("[%8s] ZSTD:   %s (%d bytes), compression ratio: %.2f%n", m.formatType, byteCountToDisplaySize(m.size), m.zstdSize, zstdCompressionRate);

                writer.write(format("none+%s,%s,%.2f%n", m.formatType, m.size, 1.0d));
                writer.write(format("gzip+%s,%s,%.2f%n", m.formatType, m.gzipSize, gzipCompressionRate));
                writer.write(format("lz4+%s,%s,%.2f%n", m.formatType, m.lz4Size, lz4CompressionRate));
                writer.write(format("snappy+%s,%s,%.2f%n", m.formatType, m.snappySize, snappyCompressionRate));
                writer.write(format("zstd+%s,%s,%.2f%n", m.formatType, m.zstdSize, zstdCompressionRate));
            }
        }
    }

    private static Measurements json() {
        var bytes = jsonRecordsAsBytes();
        var gzipCompressed = bytes.stream().map(gzipCompressor::compress).collect(toList());
        var lz4Compressed = bytes.stream().map(lz4Compressor::compress).collect(toList());
        var snappyCompressed = bytes.stream().map(snappyCompressor::compress).collect(toList());
        var zstdCompressed = bytes.stream().map(zstdCompressor::compress).collect(toList());

        return Measurements.builder()
                .formatType(JSON)
                .size(length(bytes))
                .gzipSize(length(gzipCompressed))
                .lz4Size(length(lz4Compressed))
                .snappySize(length(snappyCompressed))
                .zstdSize(length(zstdCompressed))
                .build();
    }

    private static Measurements proto() {
        var bytes = protoRecordsAsBytes();
        var gzipCompressed = bytes.stream().map(gzipCompressor::compress).collect(toList());
        var lz4Compressed = bytes.stream().map(lz4Compressor::compress).collect(toList());
        var snappyCompressed = bytes.stream().map(snappyCompressor::compress).collect(toList());
        var zstdCompressed = bytes.stream().map(zstdCompressor::compress).collect(toList());

        return Measurements.builder()
                .formatType(PROTOBUF)
                .size(length(bytes))
                .gzipSize(length(gzipCompressed))
                .lz4Size(length(lz4Compressed))
                .snappySize(length(snappyCompressed))
                .zstdSize(length(zstdCompressed))
                .build();
    }

    private static Measurements avro() {
        var bytes = avroRecordsAsBytes();
        var gzipCompressed = bytes.stream().map(gzipCompressor::compress).collect(toList());
        var lz4Compressed = bytes.stream().map(lz4Compressor::compress).collect(toList());
        var snappyCompressed = bytes.stream().map(snappyCompressor::compress).collect(toList());
        var zstdCompressed = bytes.stream().map(zstdCompressor::compress).collect(toList());

        return Measurements.builder()
                .formatType(AVRO)
                .size(length(bytes))
                .gzipSize(length(gzipCompressed))
                .lz4Size(length(lz4Compressed))
                .snappySize(length(snappyCompressed))
                .zstdSize(length(zstdCompressed))
                .build();
    }

    private static int length(List<byte[]> records) {
        return records.stream().mapToInt(it -> it.length).sum();
    }
}
