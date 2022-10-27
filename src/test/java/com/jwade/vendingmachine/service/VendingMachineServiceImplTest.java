package com.jwade.vendingmachine.service;

import com.jwade.vendingmachine.dao.AuditDao;
import com.jwade.vendingmachine.dao.VendingMachineDao;
import com.jwade.vendingmachine.dao.VendingMachinePersistenceException;
import com.jwade.vendingmachine.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceImplTest {

    class VendingMachineDaoTestImpl implements VendingMachineDao {

        @Override
        public Item getItem(String name) throws VendingMachinePersistenceException {
            if ("item".equals(name)){
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

    class AuditDaoTestDaoImpl implements AuditDao {

        @Override
        public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {}
    }

    private VendingMachineServiceImpl service;

    @BeforeEach
    void setUp() {
        VendingMachineDao dao = new VendingMachineDaoTestImpl();
        AuditDao auditDao = new AuditDaoTestDaoImpl();

        service = new VendingMachineServiceImpl(dao, auditDao);
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
        Item item1 = new Item();
        item1.setCost(BigDecimal.valueOf(1.59));
        item1.setNumInventoryItems(3);

        assertThrows(VendingMachineDataValidationException.class, () -> service.addItem(item1));

    }

    @Test
    public void testAddItemEntryNullCost () {
        Item item = new Item();
        item.setNumInventoryItems(3);

        assertThrows(VendingMachineDataValidationException.class, () -> service.addItem(item));
    }

    @Test
    public void testAddItemEntryNullInventory () {
        Item item = new Item();
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


        assertThrows(VendingMachineInsufficientFundsException.class, () -> service.sellItem(BigDecimal.valueOf(0), item));

    }

    @Test
    public void testSellItemNoInventory() {
        Item item = new Item();
        item.setNumInventoryItems(0);
        item.setCost(BigDecimal.valueOf(2.50));

       assertThrows(VendingMachineItemInventoryException.class, () -> service.sellItem(BigDecimal.valueOf(5.50), item));
    }

    @Test
    public void testSellItemSuccess() throws VendingMachineInsufficientFundsException, VendingMachinePersistenceException, VendingMachineItemInventoryException {
        BigDecimal funds = BigDecimal.valueOf(5.50);
        Item item = new Item();
        item.setNumInventoryItems(6);
        item.setCost(BigDecimal.valueOf(2.50));


        BigDecimal difference = service.sellItem(funds, item);
        assertEquals(BigDecimal.valueOf(3.0), difference);
    }

}