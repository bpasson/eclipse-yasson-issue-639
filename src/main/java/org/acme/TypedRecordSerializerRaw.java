package org.acme;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;
import org.eclipse.yasson.internal.ProcessingContext;
import org.eclipse.yasson.internal.model.ClassModel;
import org.eclipse.yasson.internal.model.PropertyModel;

public class TypedRecordSerializerRaw implements JsonbSerializer<TypedRecord> {

    @Override
    public void serialize(TypedRecord typedRecord, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
        ProcessingContext processingContext = (ProcessingContext) serializationContext;
        Object entity = typedRecord.getValue();

        if (!processingContext.addProcessedObject(entity)) {
            throw new RuntimeException("Cyclic dependency when marshaling an object");
        }

        try {
            jsonGenerator.writeStartObject();
            ClassModel classModel = processingContext.getMappingContext().getOrCreateClassModel(entity.getClass());

            for (PropertyModel property : classModel.getSortedProperties()) {
                if (property.isReadable()) {
                    writeValue(property.getWriteName(), property.getValue(entity), jsonGenerator, serializationContext);
                }
            }

            writeValue("foo", "bar", jsonGenerator, serializationContext);

            jsonGenerator.writeEnd();
        } finally {
            processingContext.removeProcessedObject(entity);
        }
    }

    private void writeValue(String name, Object value, JsonGenerator generator, SerializationContext context) {
        if (value == null) {
            generator.writeNull(name);
        } else {
            context.serialize(name, value, generator);
        }
    }
}
