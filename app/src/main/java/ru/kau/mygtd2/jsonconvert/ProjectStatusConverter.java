package ru.kau.mygtd2.jsonconvert;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import ru.kau.mygtd2.common.enums.PrStatus;


public class ProjectStatusConverter implements JsonSerializer<PrStatus>, JsonDeserializer<PrStatus> {
    @Override
    public JsonElement serialize(PrStatus src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.Value);
    }

    @Override
    public PrStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //return null;
        return PrStatus.from(json.getAsInt());
    }
}
