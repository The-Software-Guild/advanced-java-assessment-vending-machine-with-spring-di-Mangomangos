package com.jwade.vendingmachine.service;

import com.jwade.vendingmachine.dao.VendingMachineDao;
import com.jwade.vendingmachine.dao.VendingMachinePersistenceException;
import com.jwade.vendingmachine.dto.Item;

import java.util.ArrayList;
import java.util.List;

class VendingMachineDaoStubImpl implements VendingMachineDao {

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        if ("item".equals(name)) {
            Item item = new Item();
            item.setName("item");
            return item;
        }
        return null;
    }

    @Override
    public List<Item> listAllItems() throws VendingMachinePersistenceException {
        Item item1 = new Item();
        Item item2 = new Item();
        Item item3 = new Item();

        item1.setNumInventoryItems(2);
        item2.setNumInventoryItems(4);
        item3.setNumInventoryItems(-5);

        List<Item> itemList = new ArrayList<>();
        itemList.add(0, item1);
        itemList.add(1, item2);
        itemList.add(2, item3);

        return itemList;
    }

    @Override
    public Item addItem(Item item) throws VendingMachinePersistenceException {
        return null;
    }

    @Override
    public Item removeItem(Item item) throws VendingMachinePersistenceException {
        return null;
    }

    @Override
    public Item changeInventoryCount(Item item, int newCount) throws VendingMachinePersistenceException {
        return null;
    }
}
