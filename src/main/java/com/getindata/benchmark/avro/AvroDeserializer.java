package com.getindata.benchmark.avro;

import com.getindata.benchmark.Deserializer;
import com.getindata.schemas.avro.TestRecord;
import lombok.SneakyThrows;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

public class AvroDeserializer implements Deserializer<TestRecord> {
    private static final DatumReader<TestRecord> DATUM_READER = new SpecificDatumReader<>(TestRecord.class);

    @SneakyThrows
    @Override
    public TestRecord convert(byte[] value) {
        var decoder = DecoderFactory.get().binaryDecoder(value, null);
        return DATUM_READER.read(null, decoder);
    }


}
