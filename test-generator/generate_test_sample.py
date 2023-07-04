import json
import os
import random
import string

FIELD_COUNT = 55
NON_NULL_FIELDS = 1.0
RECORD_COUNT = 1000


def get_random_string(length):
    # choose from all lowercase letter
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for _ in range(length))


def generate_json_records(count):
    records = []
    for record_number in range(count):
        record = {}
        for field_number in range(1, FIELD_COUNT + 1):
            record[f"field_{field_number}"] = None if random.random() > NON_NULL_FIELDS else get_random_string(15)
        records.append(json.dumps(record) + "\n")
    return records


def generate_java_class():
    content = """
package com.getindata.schemas.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestRecord {
"""

    for field_number in range(1, FIELD_COUNT + 1):
        content += f"\tString field_{field_number};\n"
    content += "}"
    return content


def generate_protobuf_schema():
    schema = """
syntax = "proto3";
package test;
option java_package = "com.getindata.schemas.proto";

message TestRecord {
"""

    for field_number in range(1, FIELD_COUNT + 1):
        schema += f"\tstring field_{field_number} = {field_number};\n"

    schema += "}"
    return schema


def generate_avro_schema():
    schema = """
{
    "name": "TestRecord",
    "type": "record",
    "namespace": "com.getindata.schemas.avro",
    "fields": [
       
"""
    fields = []
    for field_number in range(1, FIELD_COUNT + 1):
        fields.append('{' + f'"name": "field_{field_number}", "type": ["null", "string"], "default": null' + '}')

    schema += ',\n'.join(fields) + "]}"
    return schema


if __name__ == "__main__":
    test_data_path = "target/test-data"
    os.makedirs(test_data_path, exist_ok=True)
    with open(os.path.join(test_data_path, "test_data.json"), mode="w") as f:
        f.writelines(generate_json_records(RECORD_COUNT))

    pojo_path = "target/generated-sources/pojo/com/getindata/schemas/pojo"
    os.makedirs(pojo_path, exist_ok=True)
    with open(os.path.join(pojo_path, "TestRecord.java"), mode="w") as f:
        f.write(generate_java_class())

    protobuf_path = "target/schemas/proto"
    os.makedirs(protobuf_path, exist_ok=True)
    with open(os.path.join(protobuf_path, "test_record.proto"), mode="w") as f:
        f.write(generate_protobuf_schema())

    avro_path = "target/schemas/avro"
    os.makedirs(avro_path, exist_ok=True)
    with open(os.path.join(avro_path, "test_record.avsc"), mode="w") as f:
        f.write(generate_avro_schema())
