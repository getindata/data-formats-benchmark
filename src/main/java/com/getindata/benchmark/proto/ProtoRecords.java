package com.getindata.benchmark.proto;

import com.getindata.schemas.proto.TestRecordOuterClass.TestRecord;

import java.util.Arrays;
import java.util.List;

import static com.getindata.benchmark.json.JsonRecords.jsonRecords;
import static com.getindata.benchmark.proto.ProtoJsonConverter.fromJson;
import static java.util.stream.Collectors.toList;

public class ProtoRecords {

    public static List<byte[]> protoRecordsAsBytes() {
        return Arrays.stream(jsonRecords())
                .map(it -> fromJson(it, TestRecord.class).toByteArray())
                .collect(toList());
    }

}
