
package com.sg.floormaster.dao;
/**
 *
 * @author Joshua Vannatter
 */
import com.sg.floormaster.dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sg.floormaster.dto.OrderDeserializer;

public class FloorMasterDaoFileImpl implements FloorMasterDao {

    private final Map<Integer, Order> ordersMap = new HashMap<>();
    private final String fileName;
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Order.class, new OrderDeserializer()).create();
    
    public FloorMasterDaoFileImpl() {
        this.fileName = "ordersDB.txt";
    }
    
    public FloorMasterDaoFileImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Order> getOrders() {
        return new ArrayList(ordersMap.values());
    }

    @Override
    public Order getOrder(int id) {
        return ordersMap.get(id);
    }

    @Override
    public Set<Integer> getIds() {
        return ordersMap.keySet();
    }
    
    @Override
    public Order addOrder(Order order) {
        ordersMap.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Order editOrder(Order order) {
        ordersMap.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Order removeOrder(Order order) {
        ordersMap.remove(order.getOrderId(), order);
        return order;
    }
    
    @Override
    public void loadFile() throws FloorMasterPersistenceException, FileNotFoundException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(this.fileName)));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Unable to load 'ordersDB.txt'. (It might not have been initialized yet)\n"
                    + "(You can initialize by adding something to the database)");
        }
        String json;
        int lineNum = 0;
        while (scanner.hasNextLine()) {
            json = scanner.nextLine();
            lineNum++;
            try{
                Order order = GSON.fromJson(json, Order.class);
                ordersMap.put(order.getOrderId(), order);
            } catch(JsonSyntaxException | NullPointerException e){
                throw new FloorMasterPersistenceException("Data possibly corrupted on line " + lineNum + " of database file.");
            }
        }
        scanner.close();
    }
    
    @Override
    public void writeFile(boolean isTraining) throws FloorMasterPersistenceException {
        writeFile(this.ordersMap, this.fileName, isTraining, "view");
    }
    
    @Override
    public void writeFile(boolean isTraining, String format) throws FloorMasterPersistenceException {
        writeFile(this.ordersMap, this.fileName, isTraining, format);
    }
    
    @Override
    public void writeFile(Map<Integer, Order> map, String fileName, boolean isTraining, String format) throws FloorMasterPersistenceException {
        if (!isTraining) {
            PrintWriter out;
            try {
                out = new PrintWriter(new FileWriter(fileName));
            } catch (IOException e) {
                throw new FloorMasterPersistenceException("Could not write to orders file.", e);
            }
            
            map.values().forEach(o -> out.println(serializeOrder(o, format)));
            out.flush();
            out.close();
        }
    }
    
    private String serializeOrder(Order order, String format) {
        String serialized = "";
        switch(format) {
            case "view":
                serialized = order.toJsonView();
                break;
            case "compact":
                serialized = order.toJsonCompact();
                break;
            case "complex":
                serialized = order.toJsonComplex();
                break;
            default:
                System.out.println("Unexpected");
                break;
        }
        return serialized;
    }
}
