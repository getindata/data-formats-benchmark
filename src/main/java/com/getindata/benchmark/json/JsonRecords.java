package com.getindata.benchmark.json;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.List;

import static com.google.common.io.Resources.getResource;
import static com.google.common.io.Resources.readLines;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class JsonRecords {

    public static List<byte[]> jsonRecordsAsBytes() {
        return jsonRecords()
                .stream()
                .map(String::getBytes)
                .collect(toList());
    }

    @SneakyThrows
    public static List<String> jsonRecords(){
        return readLines(getResource("test_data.json"), defaultCharset());
    }
}
