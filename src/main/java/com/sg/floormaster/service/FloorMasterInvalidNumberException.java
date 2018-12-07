
package com.sg.floormaster.service;

/**
 *
 * @author Joshua Vannatter
 */
public class FloorMasterInvalidNumberException extends Exception {

    public FloorMasterInvalidNumberException(String message, Throwable cause) {
        super(message, cause);
    }

   
    public FloorMasterInvalidNumberException(String msg) {
        super(msg);
    }
}
