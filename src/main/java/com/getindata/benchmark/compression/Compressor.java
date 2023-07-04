package com.getindata.benchmark.compression;

public interface Compressor {
    byte[] compress(byte[] input);

    byte[] decompress(byte[] input);

    class NoopCompressor implements Compressor {

        @Override
        public byte[] compress(byte[] input) {
            return input;
        }

        @Override
        public byte[] decompress(byte[] input) {
            return input;
        }
    }

    static Compressor byName(String name) {
        switch (name) {
            case "none":
                return new NoopCompressor();
            case "gzip":
                return new GZipCompressor();
            case "lz4":
                return new Lz4Compressor();
            case "snappy":
                return new SnappyCompressor();
            case "zstd":
                return new ZstdCompressor();
            default:
                throw new IllegalArgumentException();
        }

    }
}
