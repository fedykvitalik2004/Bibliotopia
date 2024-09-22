package org.vitalii.fedyk.bibliotopia.utility.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Objects;

public class CapitalizeDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final String value = jsonParser.getValueAsString();
        if (Objects.isNull(value)) {
            return null;
        }
        return value.isEmpty() ? value : capitalize(value);
    }

    private String capitalize(final String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }
}
