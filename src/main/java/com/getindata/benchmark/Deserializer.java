package com.getindata.benchmark;

public interface Deserializer<T> {
    T convert(byte[] value);

}
