/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.dao;

import com.sg.floormaster.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Joshua Vannatter
 */
public class FloorMasterDaoStubImpl implements FloorMasterDao {
    
    private final Order onlyOrder;
    private final Map<Integer, Order> ordersMap = new HashMap<>();
    
    public FloorMasterDaoStubImpl(){
        onlyOrder = new Order();
        onlyOrder.setDate(LocalDate.MAX);
        onlyOrder.setCustomerName("Johnny Boi");
        onlyOrder.setState("OH");
        onlyOrder.setProductType("Wood");
        onlyOrder.setArea(BigDecimal.ONE);
        
        ordersMap.put(onlyOrder.getOrderId(), onlyOrder);
    }

    @Override
    public List<Order> getOrders() {
        return new ArrayList(ordersMap.values());
    }

    @Override
    public Order getOrder(int id) {
        if(id == onlyOrder.getOrderId()){
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Set<Integer> getIds() {
        return ordersMap.keySet();
    }

    @Override
    public Order addOrder(Order order) {
        if(order == ordersMap.get(onlyOrder.getOrderId())){
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(Order order) {
        return order;
    }

    @Override
    public Order removeOrder(Order order) {
        if(order == ordersMap.get(onlyOrder.getOrderId())){
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public void loadFile() throws FloorMasterPersistenceException {
        //nothing
    }

    @Override
    public void writeFile(boolean isTraining) throws FloorMasterPersistenceException {
        //nothing
    }
    
    @Override
    public void writeFile(boolean isTraining, String format) throws FloorMasterPersistenceException {
        //nothing
    }
    
    @Override
    public void writeFile(Map<Integer, Order> map, String fileName, boolean isTraining, String format) throws FloorMasterPersistenceException {
        //nothing
    }
}
