package com.getindata.benchmark.proto;

import com.google.protobuf.AbstractMessage.Builder;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProtoJsonConverter {

    @SneakyThrows
    public static <T extends Message> T fromJson(String json, Class<T> clazz) {
        var builder = (Builder<?>) clazz.getMethod("newBuilder").invoke(null);
        JsonFormat.parser().merge(json, builder);
        return (T) builder.build();
    }
}