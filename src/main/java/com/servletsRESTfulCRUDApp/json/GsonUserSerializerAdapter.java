package com.servletsRESTfulCRUDApp.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.servletsRESTfulCRUDApp.model.User;

import java.lang.reflect.Type;

public class GsonUserSerializerAdapter implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", user.getId());
        jsonObject.addProperty("name", user.getName());

        return jsonObject;
    }
}
