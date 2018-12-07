
package com.sg.floormaster.dto;

import java.math.BigDecimal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.util.Objects;
import static com.sg.floormaster.dto.OrderCalculator.*;

/**
 *
 * @author Joshua Vannatter
 */
public class Order {

    private int orderId;
    private String date;
    private String customerName;
    private String state;
    private String productType;
    private String area;
    
    private transient BigDecimal taxRate;
    private transient BigDecimal materialCostPerSqFt;
    private transient BigDecimal laborCost;
    private transient BigDecimal laborCostPerSqFt;
    private transient BigDecimal materialCost;
    private transient BigDecimal tax;
    private transient BigDecimal total;
    
    private transient static int startID = 0;
    private transient static Gson gsonView = new GsonBuilder().registerTypeAdapter(Order.class, new OrderSerializerForView())
            .setPrettyPrinting().create();
    
    private transient static Gson gsonFile =  new GsonBuilder().registerTypeAdapter(Order.class, new OrderSerializerForFileSave())
            .setPrettyPrinting().create();
    
    private transient static Gson gsonCompact =  new GsonBuilder().create();
    
    public Order(int orderId, String date, String customerName, String state, String productType, String area) {
        
        setOrderId(orderId);
        this.date = date;
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
    }

    public static int getStartId() {
        return Order.startID;
    }

    public Order() {
        setOrderId(generateId());
    }

    public int getOrderId() {
        return orderId;
    }

    private void setOrderId(int id) {
        this.orderId = id;
        if (startID < id) {
            startID = id;
        }
    }

    private static int generateId() {
        return ++startID;
    }

    public LocalDate getDate() {
        return LocalDate.parse(this.date);
    }

    public void setDate(LocalDate date) {
        this.date = date.toString();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return new BigDecimal(area);
    }

    public void setArea(BigDecimal area) {
        this.area = area.toString();
    }
    
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getMaterialCostPerSqFt() {
        return materialCostPerSqFt;
    }

    public void setMaterialCostPerSqFt(BigDecimal costPerSqFt) {
        this.materialCostPerSqFt = costPerSqFt;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    private void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    private void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    private void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    private void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public void setDependencies() {
        setLaborCost(calcLaborCost(getArea(), getLaborCostPerSqFt()));
        setMaterialCost(calcMaterialCost(getArea(), getMaterialCostPerSqFt()));
        setTax(calcTax(getLaborCost(), getMaterialCost(), getTaxRate()));
        setTotal(calcTotal(getLaborCost(), getMaterialCost(), getTax()));
    }

    
    
    public String toSimpleString() {
        return "Name: " + customerName + ", State: " + state + ", Type: " + productType + ", Area: " + area;
    }
    
    public String toJsonView() {
        return gsonView.toJson(this);
    }
    
    public String toJsonComplex() {
        return gsonFile.toJson(this);
    }
    
    public String toJsonCompact() {
        return gsonCompact.toJson(this);
    }
    
    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", date=" + date + ", customerName=" + customerName + ", state=" + state + ", taxRate=" + taxRate + ", productType=" + productType + ", area=" + area + ", costPerSqFt=" + materialCostPerSqFt + ", laborCost=" + laborCost + ", laborCostPerSqFt=" + laborCostPerSqFt + ", materialCost=" + materialCost + ", tax=" + tax + ", total=" + total + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.orderId;
        hash = 31 * hash + Objects.hashCode(this.date);
        hash = 31 * hash + Objects.hashCode(this.customerName);
        hash = 31 * hash + Objects.hashCode(this.state);
        hash = 31 * hash + Objects.hashCode(this.productType);
        hash = 31 * hash + Objects.hashCode(this.area);
        hash = 31 * hash + Objects.hashCode(this.taxRate);
        hash = 31 * hash + Objects.hashCode(this.materialCostPerSqFt);
        hash = 31 * hash + Objects.hashCode(this.laborCost);
        hash = 31 * hash + Objects.hashCode(this.laborCostPerSqFt);
        hash = 31 * hash + Objects.hashCode(this.materialCost);
        hash = 31 * hash + Objects.hashCode(this.tax);
        hash = 31 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.materialCostPerSqFt, other.materialCostPerSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSqFt, other.laborCostPerSqFt)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }
    
    
}
