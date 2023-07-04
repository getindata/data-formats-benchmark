package com.getindata.benchmark.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.getindata.benchmark.proto.ProtoRecords.protoRecordsAsBytes;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ProtoBenchmark {

    private static final ProtoDeserializer PROTO_CONVERTER = new ProtoDeserializer();

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        public List<byte[]> inputRecords;

        @Setup
        public void doSetup() {
            inputRecords = protoRecordsAsBytes();
        }
    }

    @Benchmark
    public void readProtoObjects(BenchmarkState state, Blackhole blackhole) throws InvalidProtocolBufferException {
        blackhole.consume(PROTO_CONVERTER.convert(state.inputRecords));
    }

}
