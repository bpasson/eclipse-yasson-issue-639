package org.acme;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SerializerTest {

    @Test
    public void rawTypeShouldNotWork() throws Exception {
        Assertions.assertEquals("{\"value\":{\"name\":\"A\"}}", JsonbHelper.withRawTypeSerializer(new TypedRecord<>(new Record("A"))));
    }

    @Test
    public void parameterizedTypeShouldWork() throws Exception {
        Assertions.assertEquals("{\"name\":\"A\",\"foo\":\"bar\"}", JsonbHelper.withParameterizedTypeSerializer(new TypedRecord<>(new Record("A"))));
    }
}
