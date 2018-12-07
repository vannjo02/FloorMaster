
package com.sg.floormaster.ui;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Joshua Vannatter
 */
public class UserIOConsoleImpl implements UserIO {
    private final Scanner input = new Scanner(System.in);
    private final Random rand = new Random();
    private String response;

    @Override
    public void print(Object... values) {
        for (Object x : values) {
            System.out.print(x);
        }
    }

    @Override
    public void println(Object... values) {
        if(values.length == 0){System.out.println("");}
        for(Object x : values){
            System.out.println(x);
        }
    }

    @Override
    public double readDouble(String prompt) {
        response = readStr(prompt);
        while (!isNumeric(response)) {
            println("\nThat's not numeric... Try again!");
            response = readStr(prompt);
        }
        try {
            return Double.parseDouble(response);
        } catch (NumberFormatException e) {
            println("Invalid Double!");
            return readDouble(prompt);
        }
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double value = readDouble(prompt);
        if (value < min || value > max) {
            println("Value must be between " + min + " and " + max + "!");
            return readDouble(prompt, min, max);
        } else {
            return value;
        }
    }

    @Override
    public float readFloat(String prompt) {
        response = readStr(prompt);
        while (!isNumeric(response)) {
            println("\nThat's not numeric... Try again!");
            response = readStr(prompt);
        }
        try {
            return Float.parseFloat(response);
        } catch (NumberFormatException e) {
            println("Invalid Float!");
            return readFloat(prompt);
        }
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float value = readFloat(prompt);
        if (value < min || value > max) {
            println("Value must be between " + min + " and " + max + "!");
            return readFloat(prompt, min, max);
        } else {
            return value;
        }
    }

    @Override
    public int readInt(String prompt) {
        response = readStr(prompt);
        while (!isInt(response)) {
            println("\nThat's not an integer... Try again!");
            response = readStr(prompt);
        }
        try {
            return Integer.parseInt(response);
        } catch (NumberFormatException e) {
            println("Invalid Integer!");
            return readInt(prompt);
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int value = readInt(prompt);
        if (value < min || value > max) {
            println("Value must be between " + min + " and " + max + "!");
            return readInt(prompt, min, max);
        } else {
            return value;
        }
    }

    @Override
    public long readLong(String prompt) {
        response = readStr(prompt);
        while (!isNumeric(response)) {
            println("\nThat's not numeric... Try again!");
            response = readStr(prompt);
        }
        try {
            return Long.parseLong(response);
        } catch (NumberFormatException e) {
            println("Invalid Long!");
            return readLong(prompt);
        }
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long value = readLong(prompt);
        if (value < min || value > max) {
            println("Value must be between " + min + " and " + max + ".");
            return readLong(prompt, min, max);
        } else {
            return value;
        }
    }

    @Override
    public String readStr(String prompt) {
        print(prompt);
        response = input.nextLine();
        return response;
    }

    @Override
    public int randInt(int min, int max) {
        //Returns a random between min and max, can be min or max. 
        return rand.nextInt((max - min) + 1) + min;
    }

    
    private boolean isNumeric(String str) {
        // checks if a string is validly numeric.
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    
    private boolean isInt(String str) {
        // checks if a string is an int
        if (str == null) {
            return false;
        }
        if (str.isEmpty()) {
            return false;
        }
        int i = 0;
        int length = str.length();
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
