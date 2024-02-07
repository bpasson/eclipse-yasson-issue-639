package org.acme;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

public class JsonbHelper {

    public static <T> String withRawTypeSerializer(TypedRecord<T> record) throws Exception {
        JsonbConfig config = new JsonbConfig().withSerializers(new TypedRecordSerializerRaw());
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            return jsonb.toJson(record);
        }
    }

    public static <T> String withParameterizedTypeSerializer(TypedRecord<T> record) throws Exception {
        JsonbConfig config = new JsonbConfig().withSerializers(new TypedRecordSerializerParameterized());
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            return jsonb.toJson(record);
        }
    }
}
