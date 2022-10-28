
package com.jwade.vendingmachine.dao;

import com.jwade.vendingmachine.dto.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jwade
 */
public class VendingMachineDaoImpl implements VendingMachineDao{

    private Map<String, Item> itemMap = new HashMap<>();
    private FileDao fileDao = new FileDaoImpl();

    private String ITEM_FILE;

    public VendingMachineDaoImpl () throws VendingMachinePersistenceException {
        String ITEM_FILE = "items.txt";
        itemMap = fileDao.readFile(ITEM_FILE);
    }

    public VendingMachineDaoImpl(FileDao fileDao) throws VendingMachinePersistenceException {
        this.fileDao = fileDao;
        itemMap = fileDao.readFile("Hey");

    }

    @Override
    public Item getItem(String name) {
        return itemMap.get(name);
    }

    @Override
    public List<Item> listAllItems() {
        return new ArrayList<>(itemMap.values());
    }

    @Override
    public Item addItem(Item item) throws VendingMachinePersistenceException {
        Item newItem = itemMap.put(item.getName(), item);
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return newItem;
    }

    @Override
    public Item removeItem(Item item) throws VendingMachinePersistenceException {
        Item removedItem = itemMap.remove(item.getName());
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return removedItem;
    }

    @Override
    public Item changeInventoryCount(Item item, int newCount) throws VendingMachinePersistenceException {
        item.setNumInventoryItems(newCount);
        Item updatedItem = itemMap.put(item.getName(),item);
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return updatedItem;

    }
}
