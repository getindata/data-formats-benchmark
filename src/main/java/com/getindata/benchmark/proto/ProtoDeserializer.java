package com.getindata.benchmark.proto;

import com.getindata.benchmark.Deserializer;
import com.getindata.schemas.proto.TestRecordOuterClass.TestRecord;
import lombok.SneakyThrows;

public class ProtoDeserializer implements Deserializer<TestRecord> {

    @SneakyThrows
    @Override
    public TestRecord convert(byte[] value) {
        return TestRecord.parseFrom(value);
    }

}
