package com.jwade.vendingmachine.dao;

import com.jwade.vendingmachine.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class VendingMachineDaoImplTest {

    private VendingMachineDaoImpl dao;
    private FileDao fileDao;


    @BeforeEach
    void setUp() throws VendingMachinePersistenceException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("dao", VendingMachineDaoImpl.class);
    }

    @Test
    public void getItemTest () {
        Item item = dao.getItem("item1");
        assertEquals(new Item("item1", BigDecimal.valueOf(1.50), 1), item);
    }

    @Test
    public void changeInventoryCountTest() throws VendingMachinePersistenceException {
        dao.changeInventoryCount(dao.getItem("item1"), 3);
        assertEquals(3, (dao.getItem("item1").getNumInventoryItems()));

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