/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Joshua Vannatter
 */
public class OrderCalculator {
    
    public static BigDecimal calcLaborCost(BigDecimal area, BigDecimal laborCostPerSqFt) {
        BigDecimal laborCost = area.multiply(laborCostPerSqFt).setScale(2, RoundingMode.HALF_UP);
        return laborCost;
    }
    
    public static BigDecimal calcMaterialCost(BigDecimal area, BigDecimal materialCostPerSqFt) {
        BigDecimal materialCost = area.multiply(materialCostPerSqFt).setScale(2, RoundingMode.HALF_UP);
        return materialCost;
    }
    
    public static BigDecimal calcTax(BigDecimal laborCost, BigDecimal materialCost, BigDecimal taxRate) {
        BigDecimal taxRateMultiplier = taxRate.movePointLeft(2);
        BigDecimal tax = laborCost.add(materialCost).multiply(taxRateMultiplier).setScale(2, RoundingMode.HALF_UP);
        return tax;
    }
    
    public static BigDecimal calcTotal(BigDecimal laborCost, BigDecimal materialCost, BigDecimal tax) {
        BigDecimal total = laborCost.add(materialCost.add(tax)).setScale(2, RoundingMode.HALF_UP);
        return total;
    }
    
}
