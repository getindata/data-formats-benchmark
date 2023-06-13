package com.getindata.benchmark.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getindata.schemas.pojo.TestRecord;

import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public TestRecord convert(String value) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(value, TestRecord.class);
    }

    public List<TestRecord> convert(String[] values) throws JsonProcessingException {
        List<TestRecord> result = new ArrayList<>(values.length);
        for (String value : values) {
            result.add(convert(value));
        }
        return result;
    }

}
