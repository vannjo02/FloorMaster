
package com.sg.floormaster.ui;

/**
 *
 * @author Joshua Vannatter
 */
public interface UserIO {
    
    void print(Object... values);
    
    void println(Object... values);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);
    
    int randInt(int min, int max);
    
    String readStr(String prompt);

}
