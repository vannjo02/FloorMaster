
package com.sg.floormaster.dao;
/**
 *
 * @author Joshua Vannatter
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class FloorMasterStatesDao {
    private Map<String, BigDecimal> stateTaxes = new HashMap<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public Set<String> getStates() {
        return stateTaxes.keySet();
    }
    
    public BigDecimal getTaxRate(String state){
        return stateTaxes.get(state);
    }
    
    public void loadStatesAndTaxes() throws FloorMasterPersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("States.txt")));
        } catch (FileNotFoundException e) {
            throw new FloorMasterPersistenceException("-_- Could not load state/taxes file.", e);
        }
        String json = "";
        while (scanner.hasNextLine()) {
            json += scanner.nextLine();
        }
        Type collectionType = new TypeToken<Map<String, BigDecimal>>() {
        }.getType();
        stateTaxes = gson.fromJson(json, collectionType);
    }
}
