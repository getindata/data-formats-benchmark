package com.getindata.benchmark.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.xerial.snappy.Snappy;

@UtilityClass
public class SnappyUtils {
    @SneakyThrows
    public static byte[] decompress(byte[] input) {
        return Snappy.uncompress(input);
    }

    @SneakyThrows
    public static byte[] compress(byte[] input) {
        return Snappy.compress(input);
    }
}
