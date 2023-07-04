package com.getindata.benchmark.compression;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipCompressor implements Compressor {

    @SneakyThrows
    public byte[] compress(byte[] input) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
             GZIPOutputStream cos = new GZIPOutputStream(baos)) {
            cos.write(input);
            cos.close();
            return baos.toByteArray();
        }
    }

    @SneakyThrows
    public byte[] decompress(byte[] input) {
        try (GZIPInputStream stream = new GZIPInputStream(new ByteArrayInputStream(input))) {
            return IOUtils.toByteArray(stream);
        }
    }

}
