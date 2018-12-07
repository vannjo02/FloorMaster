
package com.sg.floormaster.dao;

import com.sg.floormaster.dto.Order;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Joshua Vannatter
 */
public interface FloorMasterDao {
    
    List<Order> getOrders();
    
    Order getOrder(int id);
    
    Set<Integer> getIds();
    
    Order addOrder(Order order);
    
    Order editOrder(Order order);
    
    Order removeOrder(Order order);
    
    void loadFile() throws FloorMasterPersistenceException, FileNotFoundException;
    
    void writeFile(boolean isTraining) throws FloorMasterPersistenceException;
    
    void writeFile(boolean isTraining, String format) throws FloorMasterPersistenceException;
    
    void writeFile(Map<Integer, Order> map, String fileName, boolean isTraining, String format) throws FloorMasterPersistenceException;
    
}
