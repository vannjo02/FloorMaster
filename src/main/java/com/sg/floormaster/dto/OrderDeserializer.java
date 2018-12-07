
package com.sg.floormaster.dto;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 *
 * @author Joshua Vannatter
 */
public class OrderDeserializer implements JsonDeserializer<Order>{
    @Override
    public Order deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jOb = json.getAsJsonObject();
        
        int id = jOb.get("orderId").getAsInt();
        String date = jOb.get("date").getAsString();
        String name = jOb.get("customerName").getAsString();
        String state = jOb.get("state").getAsString();
        String productType = jOb.get("productType").getAsString();
        String area = jOb.get("area").getAsString();
        
        return new Order(id, date, name, state, productType, area);
    }
}
