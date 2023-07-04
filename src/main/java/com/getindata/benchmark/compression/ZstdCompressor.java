package com.getindata.benchmark.compression;

import com.github.luben.zstd.Zstd;
import com.github.luben.zstd.ZstdCompressCtx;
import com.github.luben.zstd.ZstdDecompressCtx;
import lombok.SneakyThrows;

public class ZstdCompressor implements Compressor {
    private final ZstdDecompressCtx decompressCtx = new ZstdDecompressCtx();
    private final ZstdCompressCtx compressCtx = new ZstdCompressCtx();


    @SneakyThrows
    public byte[] decompress(byte[] input) {
        long originalSize = Zstd.decompressedSize(input);
        return decompressCtx.decompress(input, (int) originalSize);
    }

    @SneakyThrows
    public byte[] compress(byte[] input) {
        return compressCtx.compress(input);
    }

}

