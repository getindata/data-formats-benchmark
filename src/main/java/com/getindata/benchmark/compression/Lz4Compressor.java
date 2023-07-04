package com.getindata.benchmark.compression;

import lombok.SneakyThrows;
import net.jpountz.lz4.LZ4CompressorWithLength;
import net.jpountz.lz4.LZ4DecompressorWithLength;
import net.jpountz.lz4.LZ4Factory;

public class Lz4Compressor implements Compressor {

    private final LZ4Factory factory = LZ4Factory.fastestInstance();
    private final LZ4CompressorWithLength compressor = new LZ4CompressorWithLength(factory.fastCompressor());
    private final LZ4DecompressorWithLength decompressor = new LZ4DecompressorWithLength(factory.fastDecompressor());

    @SneakyThrows
    public byte[] decompress(byte[] input) {
        return decompressor.decompress(input);
    }

    @SneakyThrows
    public byte[] compress(byte[] input) {
        return compressor.compress(input);
    }
}
