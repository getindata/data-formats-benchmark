package com.getindata.benchmark;

import com.getindata.benchmark.json.JsonRecords;
import com.getindata.benchmark.proto.ProtoRecords;
import com.getindata.benchmark.utils.GZipUtils;
import com.getindata.benchmark.utils.Lz4Utils;
import com.getindata.benchmark.utils.SnappyUtils;
import com.getindata.benchmark.utils.ZstdUtils;
import lombok.Builder;
import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class SizeBenchmarks {

    enum FormatType {
        AVRO, PROTOBUF, JSON
    }


    @Data
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
        final List<Measurements> measurements = List.of(json(), proto());
        process(measurements);
    }

    private static void process(List<Measurements> measurements) throws IOException {
        try (FileWriter writer = new FileWriter("size_benchmark.csv")) {
            for (Measurements m : measurements) {
                var gzipCompressionRate = ((double) m.size) / ((double) m.gzipSize);
                var lz4CompressionRate = ((double) m.size) / ((double) m.lz4Size);
                var snappyCompressionRate = ((double) m.size) / ((double) m.snappySize);
                var zstdCompressionRate = ((double) m.size) / ((double) m.zstdSize);

                System.out.printf("[%8s] NONE:   %s, compression ratio: %.2f%n", m.formatType, m.size, 1.0d); //TODO human friendly
                System.out.printf("[%8s] GZIP:   %s, compression ratio: %.2f%n", m.formatType, m.gzipSize, gzipCompressionRate);
                System.out.printf("[%8s] LZ4:    %s, compression ratio: %.2f%n", m.formatType, m.lz4Size, lz4CompressionRate);
                System.out.printf("[%8s] SNAPPY: %s, compression ratio: %.2f%n", m.formatType, m.snappySize, snappyCompressionRate);
                System.out.printf("[%8s] ZSTD:   %s, compression ratio: %.2f%n", m.formatType, m.zstdSize, zstdCompressionRate);
                writer.write(format("none+%s,%s,%.2f%n", m.formatType, m.size, 1.0d));
                writer.write(format("gzip+%s,%s,%.2f%n", m.formatType, m.gzipSize, gzipCompressionRate));
                writer.write(format("lz4+%s,%s,%.2f%n", m.formatType, m.lz4Size, lz4CompressionRate));
                writer.write(format("snappy+%s,%s,%.2f%n", m.formatType, m.snappySize, snappyCompressionRate));
                writer.write(format("zstd+%s,%s,%.2f%n", m.formatType, m.zstdSize, zstdCompressionRate));
            }
        }
    }

    private static Measurements json(){
        var bytes = JsonRecords.jsonRecordsAsBytes();
        var gzipCompressed = bytes.stream().map(GZipUtils::compress).collect(toList());
        var lz4Compressed = bytes.stream().map(Lz4Utils::compress).collect(toList());
        var snappyCompressed = bytes.stream().map(SnappyUtils::compress).collect(toList());
        var zstdCompressed = bytes.stream().map(ZstdUtils::compress).collect(toList());

        return Measurements.builder()
                .formatType(FormatType.JSON)
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

    private static Measurements proto(){
        var bytes = ProtoRecords.protoRecordsAsBytes();
        var gzipCompressed = bytes.stream().map(GZipUtils::compress).collect(toList());
        var lz4Compressed = bytes.stream().map(Lz4Utils::compress).collect(toList());
        var snappyCompressed = bytes.stream().map(SnappyUtils::compress).collect(toList());
        var zstdCompressed = bytes.stream().map(ZstdUtils::compress).collect(toList());

        return Measurements.builder()
                .formatType(FormatType.PROTOBUF)
                .size(length(bytes))
                .gzipSize(length(gzipCompressed))
                .lz4Size(length(lz4Compressed))
                .snappySize(length(snappyCompressed))
                .zstdSize(length(zstdCompressed))
                .build();
    }
}
