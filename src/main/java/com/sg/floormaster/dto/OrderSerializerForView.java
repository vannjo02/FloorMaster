
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
public class OrderSerializerForView implements JsonSerializer<Order> {

    @Override
    public JsonElement serialize(Order order, Type type, JsonSerializationContext context) {
        JsonObject jOb = new JsonObject();
        jOb.addProperty("OrderID", order.getOrderId());
        jOb.addProperty("Date", order.getDate().toString());
        jOb.addProperty("CustomerName", order.getCustomerName());
        jOb.addProperty("State", order.getState());
        jOb.addProperty("StateTax", order.getTaxRate());
        jOb.addProperty("ProductType", order.getProductType());
        jOb.addProperty("Area", order.getArea());
        jOb.addProperty("MaterialCostPerSqFt", order.getMaterialCostPerSqFt());
        jOb.addProperty("MaterialCostTotal", order.getMaterialCost());
        jOb.addProperty("LaborCostPerSqFt", order.getLaborCostPerSqFt());
        jOb.addProperty("LaborCostTotal", order.getLaborCost());
        jOb.addProperty("TaxTotal", order.getTax());
        jOb.addProperty("TotalCost", order.getTotal());
        return jOb;
    }
}
