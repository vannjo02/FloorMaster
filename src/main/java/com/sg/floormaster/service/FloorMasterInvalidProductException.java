
package com.sg.floormaster.service;
/**
 *
 * @author Joshua Vannatter
 */
public class FloorMasterInvalidProductException extends Exception {
    
    public FloorMasterInvalidProductException(String message, Throwable cause) {
        super(message, cause);
    }

   
    public FloorMasterInvalidProductException(String msg) {
        super(msg);
    }
}
