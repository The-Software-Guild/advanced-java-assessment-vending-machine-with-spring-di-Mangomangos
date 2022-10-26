
package com.jwade.vendingmachine.service;

import com.jwade.vendingmachine.dao.VendingMachineException;
import com.jwade.vendingmachine.dao.VendingMachinePersistenceException;
import com.jwade.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jwade
 */
public interface VendingMachineService {
    Item getItem(String name) throws VendingMachineException,VendingMachineItemInventoryException, VendingMachinePersistenceException;
    List<Item> listAllItems() throws VendingMachineException, VendingMachinePersistenceException;
    Item addItem(Item item) throws  VendingMachineDataValidationException, VendingMachineDuplicateItemException, VendingMachineException, VendingMachinePersistenceException;
    Item removeItem(Item item) throws VendingMachineException, VendingMachinePersistenceException;
    Item changeInventoryCount(Item item, int newCount) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachinePersistenceException;
    BigDecimal sellItem(BigDecimal totalFunds,Item item) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException, VendingMachinePersistenceException;
}
