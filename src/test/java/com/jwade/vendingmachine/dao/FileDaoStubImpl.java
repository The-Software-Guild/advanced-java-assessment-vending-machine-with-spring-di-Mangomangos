package com.jwade.vendingmachine.dao;

import com.jwade.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class FileDaoStubImpl implements FileDao {

    @Override
    public Item unmarshallItem(String line) throws VendingMachinePersistenceException {
        return null;
    }

    @Override
    public String marshallItem(Item item) {
        return null;
    }

    @Override
    public void writeFile(ArrayList<Item> items) throws VendingMachinePersistenceException {

    }

    @Override
    public Map<String, Item> readFile(String fileName) throws VendingMachinePersistenceException {

        Map<String, Item> itemsMap = new HashMap<>();
        itemsMap.put("item1", new Item("item1", BigDecimal.valueOf(1.50), 1));
        itemsMap.put("item2", new Item("item2", BigDecimal.valueOf(3.50), 4));

        return itemsMap;
    }
}
