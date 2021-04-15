package vott.models.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;

public class OffsetDateTimeAdapter implements JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {

    @Override
    public JsonElement serialize(OffsetDateTime offsetDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(offsetDateTime.toString());
    }

    @Override
    public OffsetDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return OffsetDateTime.parse(jsonElement.getAsString());
    }
}
