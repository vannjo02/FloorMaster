/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.dao;

import com.sg.floormaster.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class FloorMasterDaoTest {
    
    private final FloorMasterDao dao;
    
    public FloorMasterDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("floorMasterDao", FloorMasterDao.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dao.getOrders().forEach(o -> dao.removeOrder(o));
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testGetOrders() throws Exception {
        Order o = new Order();
        dao.addOrder(o);
        Order o2 = new Order();
        dao.addOrder(o2);
        List<Order> fromDao = dao.getOrders();
        assertEquals(fromDao.contains(o), fromDao.contains(o2));
    }

    
    @Test
    public void testGetOrder() {
        Order o = new Order();
        dao.addOrder(o);
        Order expected = o;
        Order fromDao = dao.getOrder(o.getOrderId());
        assertEquals(expected, fromDao);
    }

    
    @Test
    public void testGetIds() throws Exception {
        Order o = new Order();
        dao.addOrder(o);
        Order o2 = new Order();
        dao.addOrder(o2);
        Set<Integer> expected = new HashSet<>(Arrays.asList(o.getOrderId(), o2.getOrderId()));
        Set<Integer> fromDao = dao.getIds();
        assertEquals(expected, fromDao);
    }

    
    @Test
    public void testAddOrder() {
        Order o = new Order();
        Order oFromDao = dao.addOrder(o);
        int expected = 1;
        int fromDao = dao.getOrders().size();
        assertEquals(expected, fromDao);
        assertEquals(o, oFromDao);
    }

    
    @Test
    public void testEditOrder() throws Exception {
        Order o = new Order();
        o.setCustomerName("test");
        dao.addOrder(o);
        o.setCustomerName("other test");
        dao.editOrder(o);
        assertEquals(o, dao.getOrder(o.getOrderId()));
    }

    
    @Test
    public void testRemoveOrder() throws Exception {
        Order o = new Order();
        o.setCustomerName("test");
        dao.addOrder(o);
        dao.removeOrder(o);
        assertNull(dao.getOrder(o.getOrderId()));
    }

    @Test
    public void testWriteLoadFile() throws Exception {
        Order o = new Order();
        o.setCustomerName("test");
        o.setArea(BigDecimal.ONE);
        o.setDate(LocalDate.MAX);
        o.setState("NA");
        o.setProductType("Material");
        dao.addOrder(o);
        dao.writeFile(false, "compact");
        dao.loadFile();
        assertEquals(o, dao.getOrder(o.getOrderId()));
    }    
}
