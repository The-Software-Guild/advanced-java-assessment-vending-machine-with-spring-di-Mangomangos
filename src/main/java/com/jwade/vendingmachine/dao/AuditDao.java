
package com.jwade.vendingmachine.dao;

/**
 *
 * @author jwade
 */
public interface AuditDao {
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
