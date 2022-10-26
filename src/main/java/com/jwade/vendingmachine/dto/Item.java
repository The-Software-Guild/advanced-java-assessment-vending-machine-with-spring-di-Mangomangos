
package com.jwade.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author jwade
 */
public class Item {
    private String name;
    private BigDecimal cost;
    private int numInventoryItems;

    public Item() {
    }

    public Item(String name, BigDecimal cost, int numInventoryItems) {
       this.name = name;
       this.cost = cost;
       this.numInventoryItems = numInventoryItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getNumInventoryItems() {
        return numInventoryItems;
    }

    public void setNumInventoryItems(int numInventoryItems) {
        this.numInventoryItems = numInventoryItems;
    }

    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", " +
                "cost=" + cost + ", " +
                "numInventoryItems=" + numInventoryItems + '}';
    }

    @Override
    public int hashCode() {
        int hash = 8;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.cost);
        hash = 89 * hash + Objects.hashCode(this.numInventoryItems);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null){
            return false;
        }

        if (getClass() != getClass()){
            return false;
        }

        final Item other = (Item) obj;
        if (!Objects.equals(this.name, other.name)){
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)){
            return false;
        }
        if (!Objects.equals(this.numInventoryItems, other.numInventoryItems)){
            return false;
        }
        return true;
    }
    
    
}
