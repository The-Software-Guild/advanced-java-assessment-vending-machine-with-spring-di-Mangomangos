
package com.jwade.vendingmachine.controller;

import com.jwade.vendingmachine.dao.VendingMachineException;
import com.jwade.vendingmachine.dao.VendingMachinePersistenceException;
import com.jwade.vendingmachine.dto.Change;
import com.jwade.vendingmachine.dto.Item;
import com.jwade.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.jwade.vendingmachine.service.VendingMachineItemInventoryException;
import com.jwade.vendingmachine.ui.VendingMachineView;
import com.jwade.vendingmachine.service.VendingMachineService;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jwade
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineService service;

    public VendingMachineController() throws VendingMachineException {
    }
    
    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }
    

    public void run(){
        BigDecimal balance = new BigDecimal(0.0);
        boolean start = true;
        try{
            while (start) {
            view.printMenu();
            view.displayBalance(balance);
            String operation = view.getMenuSelection();
            switch(operation) {
                case "1":  // add funds
                    balance=addFunds(balance);
                    break;
                case "2":  // buy items
                    try{
                        balance=buyItems(balance);
                    }catch(VendingMachineItemInventoryException | VendingMachineInsufficientFundsException |
                           VendingMachinePersistenceException e){
                        view.displayBalance(balance);
                        view.displayErrorMessage(e.getMessage());
                    }
                    break;
                case "3": // quit
                    try{
                        quit(balance);
                    }catch(VendingMachineInsufficientFundsException e){
                        view.displayBalance(balance);
                        view.displayErrorMessage(e.getMessage());
                    }
                    start = false;
                    break;
                default:
                    view.displayUnknownCommand();
            }
        }
        }catch(VendingMachineException e){
            view.displayErrorMessage(e.getMessage());
        }

    }
    
    public BigDecimal addFunds(BigDecimal balance){
        return balance.add(view.displayAndGetFunds());
    }
    
    public BigDecimal buyItems(BigDecimal balance) throws VendingMachinePersistenceException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException, VendingMachineException {
       List<Item> itemList = service.listAllItems();
       view.printAllItems(itemList);
       int selection = view.getItemSelection(1, itemList.size());
       Item chosenItem = itemList.get(selection-1);
       BigDecimal newBalance = service.sellItem(balance, chosenItem);
       view.purchaseSucceeded();
       return newBalance;
    }
    
    public void quit(BigDecimal balance) throws VendingMachineInsufficientFundsException{
        if (balance.compareTo(BigDecimal.valueOf(0)) > 0){
            Change change = new Change();
            view.printChanges(change.getChange(balance));
            }
        view.displayQuitMessage();

    }
}
