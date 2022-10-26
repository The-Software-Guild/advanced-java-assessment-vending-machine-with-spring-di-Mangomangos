
package com.jwade.vendingmachine.service;

/**
 *
 * @author jwade
 */
public class VendingMachineItemInventoryException extends Exception{
    public VendingMachineItemInventoryException(String msg){
        super(msg);
    }
    
    public VendingMachineItemInventoryException(String msg, Throwable cause){
        super(msg,cause);
    }
}
