
package com.jwade.vendingmachine.dao;

import com.jwade.vendingmachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author jwade
 */
public class FileDaoImpl implements FileDao{

    private static final String ITEM_FILE = "items.txt";
    private static final String DELIMITER = ",";

    private Map<String, Item > items = new HashMap<>();

    public FileDaoImpl() {
    }

    @Override
    public Item unmarshallItem(String line) throws VendingMachinePersistenceException{

        String[] itemTokens = line.split(DELIMITER);
        String itemName = itemTokens[0];
        BigDecimal cost = BigDecimal.valueOf(Double.parseDouble(itemTokens[1]));
        int numOfItems = Integer.parseInt(itemTokens[2]);

        return new Item(itemName, cost, numOfItems);
    }

    @Override
    public String marshallItem(Item item) {

        return item.getName() + DELIMITER + item.getCost() + DELIMITER + item.getNumInventoryItems();
    }

    @Override
    public void writeFile(ArrayList<Item> items) throws VendingMachinePersistenceException{

        PrintWriter out;

         try {
            out = new PrintWriter( new FileWriter(ITEM_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save item data", e
            );
        }

         String itemAsText;
         for (Item currentItem: items){
             itemAsText = marshallItem(currentItem);
             out.println(itemAsText);
             out.flush();
         }
         out.close();
    }

    @Override
    public Map<String, Item> readFile(String file) throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_FILE)
                    )
            );
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "Could not load list of items into memory", e
            );
        }

        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getName(), currentItem);
        }

        scanner.close();
        return items;
    }

    
}
