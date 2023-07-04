package com.getindata.benchmark.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getindata.schemas.pojo.TestRecord;

import java.util.ArrayList;
import java.util.List;

public class JsonDeserializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public TestRecord convert(byte[] value) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(new String(value), TestRecord.class);
    }

    public List<TestRecord> convert(List<byte[]> values) throws JsonProcessingException {
        List<TestRecord> result = new ArrayList<>(values.size());
        for (byte[] value : values) {
            result.add(convert(value));
        }
        return result;
    }

}
