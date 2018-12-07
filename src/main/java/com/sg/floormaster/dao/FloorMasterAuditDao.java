
package com.sg.floormaster.dao;

/**
 *
 * @author Joshua Vannatter
 */
public interface FloorMasterAuditDao {
    
    public void writeAuditEntry(String entry) throws FloorMasterPersistenceException;
    
}
