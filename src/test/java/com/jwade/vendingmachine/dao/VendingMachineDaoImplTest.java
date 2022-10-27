package com.jwade.vendingmachine.dao;

import com.jwade.vendingmachine.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class VendingMachineDaoImplTest {

    class FileDaoTestImpl implements FileDao {

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
            itemsMap.put("item1",new Item("item1", BigDecimal.valueOf(1.50), 1));
            itemsMap.put("item2",new Item("item2", BigDecimal.valueOf(3.50), 4));

            return itemsMap;
        }
    }

    private VendingMachineDaoImpl dao;
    private FileDao fileDao;

    private Map<String, Item> itemMap = new HashMap<>();


    @BeforeEach
    void setUp() throws VendingMachinePersistenceException {

        fileDao = new FileDaoTestImpl();
        dao = new VendingMachineDaoImpl(fileDao, "Hey");
        itemMap = fileDao.readFile("Hey");


    }

    @Test
    public void getItemTest () {
        Item item = dao.getItem("item1");
        assertEquals(new Item("item1", BigDecimal.valueOf(1.50), 1), item);
    }

    @Test
    public void changeInventoryCountTest(){
        Item item = dao.getItem("item1");
        item.setNumInventoryItems(3);
        assertEquals(3, item.getNumInventoryItems());

    }

    @Test
    public void addItemTest() throws VendingMachinePersistenceException {
        dao.addItem(new Item("item3", BigDecimal.valueOf(1.50), 5));
        List<Item> allItems = dao.listAllItems();

        assertEquals(3, allItems.size());
        assertTrue(allItems.contains(new Item("item3", BigDecimal.valueOf(1.50), 5)));

    }

    @Test
    public void testRemoveItem() throws VendingMachinePersistenceException {
        dao.removeItem(dao.getItem("item2"));
        List<Item> allItems = dao.listAllItems();

        assertEquals(1, allItems.size());
        assertTrue(allItems.contains(new Item("item1", BigDecimal.valueOf(1.50), 1)));
        assertFalse(allItems.contains(new Item("item2", BigDecimal.valueOf(3.50), 4)));

    }

    @Test
    public void testListAllItems(){
        List<Item> allItems = dao.listAllItems();
        assertEquals(2, allItems.size());
        assertTrue(allItems.contains(new Item("item1", BigDecimal.valueOf(1.50), 1)));
        assertTrue(allItems.contains(new Item("item2", BigDecimal.valueOf(3.50), 4)));
    }


}