package ru.kau.mygtd2.jsonconvert;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import ru.kau.mygtd2.common.enums.TypeOfTask;

public class TypeOfTaskConverter implements JsonSerializer<TypeOfTask>, JsonDeserializer<TypeOfTask> {
    @Override
    public JsonElement serialize(TypeOfTask src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.Value);
    }


    @Override
    public TypeOfTask deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //System.out.println(json.);
        return TypeOfTask.from(json.getAsInt());
    }
}