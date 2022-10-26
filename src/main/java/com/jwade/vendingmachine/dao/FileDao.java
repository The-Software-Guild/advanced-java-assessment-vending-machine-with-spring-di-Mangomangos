
package com.jwade.vendingmachine.dao;

import com.jwade.vendingmachine.dto.Item;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jwade
 */
public interface FileDao {
    public Item unmarshallItem(String line) throws VendingMachinePersistenceException;
    public String marshallItem(Item item);
    void writeFile(ArrayList<Item> items) throws VendingMachinePersistenceException;

    public Map<String, Item> readFile(String file) throws VendingMachinePersistenceException;
}
