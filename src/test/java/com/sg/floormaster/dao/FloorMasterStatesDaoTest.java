/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
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
public class FloorMasterStatesDaoTest {
    
    private final FloorMasterStatesDao statesDao;
    
    public FloorMasterStatesDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        statesDao = ctx.getBean("statesDao", FloorMasterStatesDao.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FloorMasterPersistenceException {
        statesDao.loadStatesAndTaxes();
    }
    
    @After
    public void tearDown() {
    }
    
    
    @Test
    public void testGetStates() {
        Set<String> set = new HashSet<>(Arrays.asList("OH", "PA", "MI", "IN"));
        assertEquals(set, statesDao.getStates());
    }
    
    @Test
    public void testGetTaxRate() {
        assertEquals(new BigDecimal("6.25"),statesDao.getTaxRate("OH"));
        assertEquals(new BigDecimal("6.75"),statesDao.getTaxRate("PA"));
        assertEquals(new BigDecimal("5.75"),statesDao.getTaxRate("MI"));
        assertEquals(new BigDecimal("6.00"),statesDao.getTaxRate("IN"));
    }    
}
