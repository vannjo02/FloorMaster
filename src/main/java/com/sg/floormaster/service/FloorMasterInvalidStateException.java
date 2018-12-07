
package com.sg.floormaster.service;
/**
 *
 * @author Joshua Vannatter
 */
public class FloorMasterInvalidStateException extends Exception {
    
    public FloorMasterInvalidStateException(String message) {
        super(message);
    }

    public FloorMasterInvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
