package com.getindata.benchmark.proto;

import com.getindata.schemas.proto.TestRecordOuterClass.TestRecord;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

public class ProtoDeserializer {

    public TestRecord convert(byte[] value) throws InvalidProtocolBufferException {
        return TestRecord.parseFrom(value);
    }

    public List<TestRecord> convert(List<byte[]> values) throws InvalidProtocolBufferException {
        List<TestRecord> result = new ArrayList<>(values.size());
        for (byte[] value : values) {
            result.add(convert(value));
        }
        return result;
    }

}
