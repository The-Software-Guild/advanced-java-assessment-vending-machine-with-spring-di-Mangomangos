package com.jwade.vendingmachine.service;

import com.jwade.vendingmachine.dao.AuditDao;
import com.jwade.vendingmachine.dao.VendingMachinePersistenceException;

class AuditDaoTestDaoStubImpl implements AuditDao {

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
    }
}
