import random
import string
import json

FIELD_COUNT = 800
NON_NULL_FIELDS = 0.2
RECORD_COUNT = 100


def get_random_string(length):
    # choose from all lowercase letter
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for i in range(length))


def generate_json_records(count):
    records = []
    for record_number in range(count):
        record = {}
        for field_number in range(1, FIELD_COUNT + 1):
            record[f"field_{field_number}"] = None if random.random() > NON_NULL_FIELDS else get_random_string(15)
        records.append(json.dumps(record) + "\n")
    return records


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


if __name__ == "__main__":
    with open("test_data.json", mode="w") as f:
        f.writelines(generate_json_records(100))

    with open("src/main/protobuf/test_record.proto", mode="w") as f:
        f.write(generate_protobuf_schema())

    # TODO generate avro schema
