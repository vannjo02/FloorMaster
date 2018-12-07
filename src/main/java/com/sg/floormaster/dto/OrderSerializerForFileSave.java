
package com.sg.floormaster.dto;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author Joshua Vannatter
 */
public class OrderSerializerForFileSave implements JsonSerializer<Order> {

    @Override
    public JsonElement serialize(Order order, Type type, JsonSerializationContext context) {
        JsonObject jOb = new JsonObject();
        jOb.addProperty("OrderID", order.getOrderId());
        jOb.addProperty("date", order.getDate().toString());
        jOb.addProperty("customerName", order.getCustomerName());
        jOb.addProperty("state", order.getState());
        jOb.addProperty("taxRate", order.getTaxRate());
        jOb.addProperty("productType", order.getProductType());
        jOb.addProperty("area", order.getArea());
        jOb.addProperty("costPerSqFt", order.getMaterialCostPerSqFt());
        jOb.addProperty("materialCost", order.getMaterialCost());
        jOb.addProperty("laborCostPerSqFt", order.getLaborCostPerSqFt());
        jOb.addProperty("laborCost", order.getLaborCost());
        jOb.addProperty("tax", order.getTax());
        jOb.addProperty("total", order.getTotal());
        return jOb;
    }
}
