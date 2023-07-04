package com.getindata.benchmark.compression;

import lombok.SneakyThrows;
import org.xerial.snappy.Snappy;

public class SnappyCompressor implements Compressor {
    @SneakyThrows
    public byte[] decompress(byte[] input) {
        return Snappy.uncompress(input);
    }

    @SneakyThrows
    public byte[] compress(byte[] input) {
        return Snappy.compress(input);
    }
}
