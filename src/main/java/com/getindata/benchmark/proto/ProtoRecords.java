package com.getindata.benchmark.proto;

import com.getindata.schemas.proto.TestRecordOuterClass.TestRecord;
import lombok.experimental.UtilityClass;

import java.util.List;

import static com.getindata.benchmark.json.JsonRecords.jsonRecords;
import static com.getindata.benchmark.proto.ProtoJsonConverter.fromJson;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class ProtoRecords {

    public static List<byte[]> protoRecordsAsBytes() {
        return jsonRecords()
                .stream()
                .map(it -> fromJson(it, TestRecord.class).toByteArray())
                .collect(toList());
    }

}
