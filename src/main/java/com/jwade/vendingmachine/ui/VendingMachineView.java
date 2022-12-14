
package com.jwade.vendingmachine.ui;

import com.jwade.vendingmachine.dto.Coins;
import com.jwade.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jwade
 */
public class VendingMachineView {
    private UserIO io;

    public VendingMachineView() {
        io=new UserIOImpl();
    }

    
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    // print main menu
    public void  printMenu() {
        mainMenuBanner();
        io.print("1. Add Money");
        io.print("2. Buy Item");
        io.print("3. Quit");
    }
    
    public String getMenuSelection()
    {
         return io.readString("Please select an operation.");
    }
    
    public int getItemSelection(int min, int max){
        return io.readInt("Please select an item", min, max);
    }
    
    // add money
    public BigDecimal displayAndGetFunds()
    {
        addMoneyBanner();
        BigDecimal funds = io.readBigDecimal("Enter funds to add ($0 - $100): ", new BigDecimal(0), new BigDecimal(100));
        io.print("Funds added");
        io.readString("Please hit enter to continue.");
        return funds;
    }
    
    // display balance
    public void displayBalance(BigDecimal bal){
        io.print("Current balance:"+bal.setScale(2, RoundingMode.DOWN));
    }
    
    public void displayErrorMessage(String message)
    {
        io.print(message + '\n');
        io.readString("Please hit enter to continue.");
    }
    
    // print all items
    public ArrayList<String> printAllItems(List<Item> itemList)
    {
        allItemsBanner();
        int j=1;
        ArrayList<String> list=new ArrayList<>();
        list.add(0,"null");
        for(Item i:itemList)
        {
            io.print(j+". "+i.toString());
            list.add(j, i.getName());
            j++;
        }
        return list;
    }
    
    public void printChanges(HashMap<Coins,Integer> map){
        io.print("Here is your change: \n");
        for(Coins c:map.keySet()){
            io.print(c.name()+"("+c.getValue()+"): "+map.get(c));
        }
    }
    
     public void displayUnknownCommand() {
        io.print("Invalid input. Please input 1, 2 or 3" + "\n");
    }
    

    // banner and message
    public void mainMenuBanner(){
        io.print("======Main Menu========");
    }
    
    public void addMoneyBanner(){
        io.print("====ADD FUNDS====");
    }
    
    public void allItemsBanner(){
        io.print("=======Vending Machine Items==========");        
    }
    
    public void purchaseSucceeded(){
        io.print("Purchase Succeeded!");
    }
    
    // goodbye
    public void displayQuitMessage()
    {
        io.print("\nGoodbye");
    }
}
