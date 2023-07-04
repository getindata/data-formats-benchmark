package com.getindata.benchmark.json;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class JsonRecords {

    public static List<byte[]> jsonRecordsAsBytes()  {
        return Arrays.stream(jsonRecords())
                .map(String::getBytes)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public static String[] jsonRecords() {
        return Files.readString(Path.of("test-data/test_data.json")).split("\n");
    }
}
