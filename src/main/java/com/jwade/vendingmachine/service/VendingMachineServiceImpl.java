
package com.jwade.vendingmachine.service;

import com.jwade.vendingmachine.dao.AuditDao;
import com.jwade.vendingmachine.dao.VendingMachineDao;
import com.jwade.vendingmachine.dao.VendingMachinePersistenceException;
import com.jwade.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jwade
 */
public class VendingMachineServiceImpl implements VendingMachineService{

    private VendingMachineDao dao;
    private AuditDao auditDao;

    public VendingMachineServiceImpl(VendingMachineDao dao, AuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException, VendingMachineItemInventoryException{
        return dao.getItem(name);
    }

    @Override
    public List<Item> listAllItems() throws VendingMachinePersistenceException{
        return dao.listAllItems()
                .stream()
                .filter(item->item.getNumInventoryItems()>0)
                .collect(Collectors.toList());
    }

    @Override
    public Item addItem(Item item) throws
            VendingMachineDuplicateItemException,
            VendingMachineDataValidationException,
            VendingMachinePersistenceException {
        if (dao.getItem(item.getName()) != null) {
            throw new VendingMachineDuplicateItemException(
                    "ERROR: Could not add item. " + item.getName() + " already exists."
            );
        }

        validateItem(item);
        dao.addItem(item);
        auditDao.writeAuditEntry(
                "Item: " + item.getName() + " added."
        );
     return item;
    }

    private void validateItem(Item item) throws VendingMachineDataValidationException{
        if ((item.getName()) == null ||
                (item.getName().trim().length() == 0) ||
                (item.getCost() == null) ||
                (item.getNumInventoryItems() < 0)
        ){
            throw new VendingMachineDataValidationException (
                "ERROR: Item name and cost cannot be blank. Number of items in inventory cannot be negative."
            );
        }
    }

    @Override
    public Item removeItem(Item item) throws VendingMachinePersistenceException {
        Item removedItem = dao.removeItem(item);
        auditDao.writeAuditEntry(
                "Item: " + item.getName() + " removed"
        );
        return removedItem;
    }

    @Override
    public Item changeInventoryCount(Item item, int newCount) throws VendingMachinePersistenceException{
        dao.changeInventoryCount(item, newCount);
        auditDao.writeAuditEntry(
                "Item: " + item.getName() + " number of inventory items changed to " + item.getNumInventoryItems()
        );
         return item;
    }

    @Override
    public BigDecimal sellItem(BigDecimal totalFunds, Item item) throws
            VendingMachineItemInventoryException, VendingMachineInsufficientFundsException, VendingMachinePersistenceException {

        if (totalFunds.compareTo(item.getCost()) < 0){
            BigDecimal difference = item.getCost().subtract(totalFunds);
            throw new VendingMachineInsufficientFundsException(
                    "You don't have enough money to buy this item. You need $" + difference + " more."
            );
        }
        if (item.getNumInventoryItems() == 0){
            throw new VendingMachineItemInventoryException(
                    "Sorry this item is not in stock."
            );
        } else {
            dao.changeInventoryCount(item, item.getNumInventoryItems()-1);
            auditDao.writeAuditEntry(
                    "Item: " +item.getName() + " has sold. Number in inventory " + item.getNumInventoryItems()
            );
            return totalFunds.subtract(item.getCost());
        }
    }
    
}
