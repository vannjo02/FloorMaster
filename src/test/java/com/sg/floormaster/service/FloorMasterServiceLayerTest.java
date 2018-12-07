/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.service;

import com.sg.floormaster.dao.FloorMasterPersistenceException;
import com.sg.floormaster.dto.Order;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Joshua Vannatter
 */
public class FloorMasterServiceLayerTest {
    
    private static FloorMasterServiceLayer service;
    
    public FloorMasterServiceLayerTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FloorMasterServiceLayer.class);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FloorMasterPersistenceException {
        service.initStates();
        service.initProducts();
    }
    
    @After
    public void tearDown() {
    }


    
    @Test
    public void testGetOrdersFromDate() throws Exception {
        assertEquals(1, service.getOrdersFromDate(LocalDate.MAX).size());
    }

    
    @Test
    public void testGetOrder() throws Exception {
        assertNotNull(service.getOrder(1));
        try {
            service.getOrder(2);
            fail("Expected FloorMasterPersistenceException was not thrown.");
        } catch (FloorMasterPersistenceException e) {
            return;
        }
    }

    
    @Test
    public void testGetIdsFromDate() throws Exception {
        assertEquals(1, service.getIdsFromDate(LocalDate.MAX).size());
        assertEquals(0, service.getIdsFromDate(LocalDate.MIN).size());
    }
    
    @Test
    public void testGetStates() {
        assertNotNull(service.getStates());
    }

    
    @Test
    public void testGetProds() {
        assertNotNull(service.getProds());        
    }
    
    @Test
    public void testAddOrder() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("state", "invalidState");
        try {
            service.addOrder(map);
            fail("Expected FloorMasterInvalidStateException was not thrown.");
        } catch (FloorMasterInvalidStateException e) {
            assertEquals(e.getClass(), FloorMasterInvalidStateException.class);
        }
        map.put("state", "OH");
        map.put("product", "invalidProductType");
        try {
            service.addOrder(map);
            fail("Expected FloorMasterInvalidProductException was not thrown.");
        } catch (FloorMasterInvalidProductException e) {
            assertEquals(e.getClass(), FloorMasterInvalidProductException.class);
        }
        map.put("product", "Wood");
        map.put("area", "invalidArea");
        try {
            service.addOrder(map);
            fail("Expected FloorMasterInvalidNumberException was not thrown.");
        } catch (FloorMasterInvalidNumberException e) {
            assertEquals(e.getClass(), FloorMasterInvalidNumberException.class);
        }
        map.put("area", "1");
        Order order = service.addOrder(map);
        assertNotEquals(order, service.getOrder(1));
    }

    
    @Test
    public void testEditOrder() throws Exception {
        Map<String, String> map = new HashMap<>();
        Order order = new Order();
        map.put("name", "someName");
        map.put("state", "invalidState");
        try {
            service.editOrder(order, map);
            fail("Expected FloorMasterInvalidStateException was not thrown.");
        } catch (FloorMasterInvalidStateException e) {
            assertEquals(e.getClass(), FloorMasterInvalidStateException.class);
        }
        map.put("state", "PA");
        map.put("product", "invalidProductType");
        try {
            service.editOrder(order, map);
            fail("Expected FloorMasterInvalidProductException was not thrown.");
        } catch (FloorMasterInvalidProductException e) {
            assertEquals(e.getClass(), FloorMasterInvalidProductException.class);
        }
        map.put("product", "Tile");
        map.put("area", "invalidArea");
        try {
            service.editOrder(order, map);
            fail("Expected FloorMasterInvalidNumberException was not thrown.");
        } catch (FloorMasterInvalidNumberException e) {
            assertEquals(e.getClass(), FloorMasterInvalidNumberException.class);
        }
        map.put("area", "2");
        Order edited = service.editOrder(order, map);
        assertEquals(order, edited);
    }

    
    @Test
    public void testRemoveOrder() throws Exception {
        Order order = service.removeOrder(service.getOrder(1));
        assertEquals(order, service.getOrder(1));
    }
    
    @Test
    public void testInitStates() throws Exception {
        service.initStates();
        assertNotNull(service.getStates());
    }
    
    @Test
    public void testInitProducts() throws Exception {
        service.initProducts();
        assertNotNull(service.getProds());
    }

    @Test
    public void testInitConfig() throws Exception {
        service.initConfig();
        assertNotNull(service.isTraining());
    }
    
    @Test
    public void testInitDatabase() throws Exception {
        service.initDatabase();
        try{
            service.getOrder(1).toJsonView();
        } catch(FloorMasterPersistenceException e){
            fail("Should have been able to serialize the order at this point");
        }
        
    }
    
    @Test
    public void testSaveDataToFiles() throws Exception {
        try{
            service.saveDataToFiles();
        } catch(FloorMasterPersistenceException e){
            fail("This should not cause an exception at this point.");
        }   
    }
}
