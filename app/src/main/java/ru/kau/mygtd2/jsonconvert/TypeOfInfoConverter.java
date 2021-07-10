package ru.kau.mygtd2.jsonconvert;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import ru.kau.mygtd2.common.enums.TypeOfInfo;


public class TypeOfInfoConverter implements JsonSerializer<TypeOfInfo> {

    @Override
    public JsonElement serialize(TypeOfInfo src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.Value);
    }


}
