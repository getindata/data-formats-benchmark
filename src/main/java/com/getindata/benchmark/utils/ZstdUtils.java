package com.getindata.benchmark.utils;

import com.github.luben.zstd.ZstdInputStream;
import com.github.luben.zstd.ZstdOutputStream;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@UtilityClass
public class ZstdUtils {

    @SneakyThrows
    public static byte[] decompress(byte[] input)  {
        try (ZstdInputStream stream = new ZstdInputStream(new ByteArrayInputStream(input))) {
            return IOUtils.toByteArray(stream);
        }
    }
    @SneakyThrows
    public static byte[] compress(byte[] input)  {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
             ZstdOutputStream cos = new ZstdOutputStream(baos)) {
            cos.write(input);
            cos.close();
            return baos.toByteArray();
        }
    }
}
