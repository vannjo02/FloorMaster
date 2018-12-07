
package com.sg.floormaster.dao;
/**
 *
 * @author Joshua Vannatter
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Scanner;

/**
 *
 * @author Joshua Vannatter
 */
public class FloorMasterProductsDao {
    private Map<String, BigDecimal[]> products = new HashMap<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public Set<String> getProds() {
        return products.keySet();
    }
    
    public BigDecimal getCostPerSqFt(String product) {
        return products.get(product)[0];
    }
    
    public BigDecimal getLaborCostPerSqFt(String product) {
        return products.get(product)[1];
    }
    
    public void loadProducts() throws FloorMasterPersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("Products.txt")));
        } catch (FileNotFoundException e) {
            throw new FloorMasterPersistenceException("-_- Could not load products file.", e);
        }
        String json = "";
        while (scanner.hasNextLine()) {
            json += scanner.nextLine();
        }
        Type collectionType = new TypeToken<Map<String, BigDecimal[]>>() {
        }.getType();
        products = gson.fromJson(json, collectionType);
    }
}
