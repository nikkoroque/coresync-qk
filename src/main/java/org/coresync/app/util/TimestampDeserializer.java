package org.coresync.app.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {
    @Override
    public Timestamp deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getText();
        return Timestamp.valueOf(value); // Convert the JSON string to Timestamp
    }
}
