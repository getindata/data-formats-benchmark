package com.getindata.benchmark.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getindata.schemas.pojo.TestRecord;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class JsonDeserializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public TestRecord convert(byte[] value) {
        return OBJECT_MAPPER.readValue(new String(value), TestRecord.class);
    }

    @SneakyThrows
    public List<TestRecord> convert(List<byte[]> values) {
        List<TestRecord> result = new ArrayList<>(values.size());
        for (byte[] value : values) {
            result.add(convert(value));
        }
        return result;
    }

}
