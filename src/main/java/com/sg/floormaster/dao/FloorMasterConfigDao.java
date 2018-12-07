/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author Joshua Vannatter
 */
public class FloorMasterConfigDao {
    
    private boolean isTraining;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public boolean getIsTraining(){
        return this.isTraining;
    }
    
    public void loadConfigFile() throws FloorMasterPersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("Config.txt")));
        } catch (FileNotFoundException e) {
            throw new FloorMasterPersistenceException("-_- Could not load configuration file.", e);
        }
        String json = "";
        while (scanner.hasNextLine()) {
            json += scanner.nextLine();
        }
        
        JsonObject fromGson = gson.fromJson(json, JsonObject.class);
        this.isTraining = fromGson.get("isTraining").getAsBoolean();
    }
}
