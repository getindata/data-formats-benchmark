package com.getindata.benchmark.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@UtilityClass
public class GZipUtils {

    @SneakyThrows
    public static byte[] compress(byte[] input) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
             GZIPOutputStream cos = new GZIPOutputStream(baos)) {
            cos.write(input);
            cos.close();
            return baos.toByteArray();
        }
    }

    @SneakyThrows
    public static byte[] decompress(byte[] input) {
        try (GZIPInputStream stream = new GZIPInputStream(new ByteArrayInputStream(input))) {
            return IOUtils.toByteArray(stream);
        }
    }

}
