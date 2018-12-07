
package com.sg.floormaster.service;
/**
 *
 * @author Joshua Vannatter
 */
import com.sg.floormaster.dao.FloorMasterPersistenceException;
import com.sg.floormaster.dto.Order;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FloorMasterServiceLayer {
    
    List<Order> getOrdersFromDate(LocalDate date) throws FloorMasterPersistenceException;
    
    Order getOrder(int id) throws FloorMasterPersistenceException;
    
    Set<Integer> getIdsFromDate(LocalDate date) throws FloorMasterPersistenceException;
    
    Set<String> getStates();
    
    Set<String> getProds();
    
    Order addOrder(Map<String, String> orderVals) throws FloorMasterPersistenceException, FloorMasterInvalidStateException,
            FloorMasterInvalidProductException, FloorMasterInvalidNumberException;
    
    Order editOrder(Order order, Map<String, String> newVals) throws FloorMasterPersistenceException, FloorMasterInvalidStateException,
            FloorMasterInvalidProductException, FloorMasterInvalidNumberException;
    
    Order removeOrder(Order order) throws FloorMasterPersistenceException;
    
    void initStates() throws FloorMasterPersistenceException;
    
    void initProducts() throws FloorMasterPersistenceException;
    
    void initConfig() throws FloorMasterPersistenceException;
    
    void initDatabase() throws FloorMasterPersistenceException, FileNotFoundException;
    
    boolean isTraining();
    
    void saveDataToFiles() throws FloorMasterPersistenceException;
}
