
package com.jwade.vendingmachine.dao;

import com.jwade.vendingmachine.dto.Item;

import java.util.List;

/**
 *
 * @author jwade
 */
public interface VendingMachineDao {
    Item getItem(String name) throws VendingMachinePersistenceException;
    List<Item> listAllItems() throws VendingMachinePersistenceException;
    Item addItem(Item item) throws VendingMachinePersistenceException;
    Item removeItem(Item item) throws VendingMachinePersistenceException;
    Item changeInventoryCount(Item item, int newCount) throws VendingMachinePersistenceException;
}
