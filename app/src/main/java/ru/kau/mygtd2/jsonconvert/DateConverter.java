package ru.kau.mygtd2.jsonconvert;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

public class DateConverter implements JsonSerializer<Date>, JsonDeserializer<Date> {


    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

        long date = src.getTime();

        return new JsonPrimitive(date);
    }

    //final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //return df.parse(json.getAsString());
        return new Date(json.getAsLong());
    }
}
