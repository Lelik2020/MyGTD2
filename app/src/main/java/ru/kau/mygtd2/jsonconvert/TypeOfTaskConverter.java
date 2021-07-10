package ru.kau.mygtd2.jsonconvert;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import ru.kau.mygtd2.common.enums.TypeOfTask;

public class TypeOfTaskConverter implements JsonSerializer<TypeOfTask> {
    @Override
    public JsonElement serialize(TypeOfTask src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.Value);
    }
}