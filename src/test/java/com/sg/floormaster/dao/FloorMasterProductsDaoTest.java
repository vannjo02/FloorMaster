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
public class FloorMasterProductsDaoTest {
    
    private final FloorMasterProductsDao productsDao;
    
    public FloorMasterProductsDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        productsDao = ctx.getBean("productsDao", FloorMasterProductsDao.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FloorMasterPersistenceException {
        productsDao.loadProducts();
    }
    
    @After
    public void tearDown() {
    }
    
    
    
    @Test
    public void testGetProds() {
        Set<String> set = new HashSet<>(Arrays.asList("Carpet", "Laminate", "Tile", "Wood"));
        assertEquals(set, productsDao.getProds());
    }
   
    @Test
    public void testGetCostPerSqFt() {
        assertEquals(new BigDecimal("2.25"), productsDao.getCostPerSqFt("Carpet"));
        assertEquals(new BigDecimal("1.75"), productsDao.getCostPerSqFt("Laminate"));
        assertEquals(new BigDecimal("3.50"), productsDao.getCostPerSqFt("Tile"));
        assertEquals(new BigDecimal("5.15"), productsDao.getCostPerSqFt("Wood"));
    }
    
    @Test
    public void testGetLaborCostPerSqFt() {
        assertEquals(new BigDecimal("2.10"), productsDao.getLaborCostPerSqFt("Carpet"));
        assertEquals(new BigDecimal("2.10"), productsDao.getLaborCostPerSqFt("Laminate"));
        assertEquals(new BigDecimal("4.15"), productsDao.getLaborCostPerSqFt("Tile"));
        assertEquals(new BigDecimal("4.75"), productsDao.getLaborCostPerSqFt("Wood"));
    }
    
}
