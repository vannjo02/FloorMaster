
package com.sg.floormaster.advice;

import com.sg.floormaster.dao.FloorMasterPersistenceException;
import org.aspectj.lang.JoinPoint;
import com.sg.floormaster.dao.FloorMasterAuditDao;

/**
 *
 * @author Joshua Vannatter
 */
public class LoggingAdvice {
    FloorMasterAuditDao auditDao;
 
    public LoggingAdvice(FloorMasterAuditDao auditDao) {
        this.auditDao = auditDao;
    }
 
    public void createAuditEntry(JoinPoint jp) {
        createAuditEntry(getJoinPointArgs(jp) + " |SUCCESS");
    }
    
    public void createErrorEntry(JoinPoint jp, Throwable ex) {
        String auditEntry = getJoinPointArgs(jp);
        auditEntry += " |ERROR: " + ex.getMessage();
        createAuditEntry(auditEntry);
    }
    
    private String getJoinPointArgs(JoinPoint jp) {
        Object[] args = jp.getArgs();
        String auditEntry = jp.getSignature().getName() + ": ";
        for (Object currentArg : args) {
            auditEntry += currentArg;
        }
        return auditEntry;
    }
    
    private void createAuditEntry(String auditEntry){
        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (FloorMasterPersistenceException e) {
            System.err.println(
               "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }
}
