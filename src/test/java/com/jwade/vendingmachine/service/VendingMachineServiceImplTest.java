package com.jwade.vendingmachine.service;

import com.jwade.vendingmachine.dao.AuditDao;
import com.jwade.vendingmachine.dao.VendingMachineDao;
import com.jwade.vendingmachine.dao.VendingMachinePersistenceException;
import com.jwade.vendingmachine.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceImplTest {

    private VendingMachineServiceImpl service;

    @BeforeEach
    void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("service", VendingMachineServiceImpl.class);
    }

    @Test
    public void testGetItem() throws VendingMachinePersistenceException, VendingMachineItemInventoryException {
            Item item = service.getItem("item");
            assertNotNull(item);
            assertEquals("item", item.getName());
    }

    @Test
    public void testListItemsWithNotZeroInventory () throws VendingMachinePersistenceException {
        List<Item> items = service.listAllItems();
        assertEquals(2,items.size());

    }

    @Test
    public void testAddItemEntryNullName () {
        Item item = new Item();
        item.setCost(BigDecimal.valueOf(1.59));
        item.setNumInventoryItems(3);

        assertThrows(VendingMachineDataValidationException.class, () -> service.addItem(item));
    }

    @Test
    public void testAddItemEntryNullCost () {
        Item item = new Item();
        item.setName("Test1");
        item.setNumInventoryItems(3);

        assertThrows(VendingMachineDataValidationException.class, () -> service.addItem(item));
    }

    @Test
    public void testAddItemEntryZeroInventory () {
        Item item = new Item();
        item.setName("Test1");
        item.setCost(BigDecimal.valueOf(1.59));

        assertThrows(VendingMachineDataValidationException.class, () -> service.addItem(item));
    }

    @Test
    public void testAddItemEntrySuccessful () {
        Item item = new Item();
        item.setName("TESTING");
        item.setCost(BigDecimal.valueOf(1.59));
        item.setNumInventoryItems(3);

        assertDoesNotThrow(() -> service.addItem(item));
    }

    @Test
    public void testAddItemEntryDuplicate () {
        Item item = new Item();
        item.setName("item");
        item.setCost(BigDecimal.valueOf(1.59));
        item.setNumInventoryItems(3);

        assertThrows(VendingMachineDuplicateItemException.class, () -> service.addItem(item));
    }

    @Test
    public void testSellItemInsufficientFundsDifferenceMaker(){
        Item item = new Item();
        item.setNumInventoryItems(5);
        item.setCost(BigDecimal.valueOf(2.50));
        BigDecimal totalFunds = BigDecimal.valueOf(0);

        assertThrows(VendingMachineInsufficientFundsException.class, () -> service.sellItem(totalFunds, item));
    }

    @Test
    public void testSellItemNoInventory() {
        Item item = new Item();
        item.setNumInventoryItems(0);
        item.setCost(BigDecimal.valueOf(2.50));
        BigDecimal totalFunds = BigDecimal.valueOf(5.50);

        assertThrows(VendingMachineItemInventoryException.class, () -> service.sellItem(totalFunds, item));
    }

    @Test
    public void testSellItemSuccess() throws VendingMachineInsufficientFundsException, VendingMachinePersistenceException, VendingMachineItemInventoryException {
        BigDecimal funds = BigDecimal.valueOf(5.50);
        Item item = new Item();
        item.setNumInventoryItems(6);
        item.setCost(BigDecimal.valueOf(2.50));

        BigDecimal difference = service.sellItem(funds, item);
        BigDecimal expected = BigDecimal.valueOf(3.0);

        assertEquals(expected, difference);
    }

}