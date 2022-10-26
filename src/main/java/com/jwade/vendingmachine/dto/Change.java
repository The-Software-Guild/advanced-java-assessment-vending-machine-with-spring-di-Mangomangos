package com.jwade.vendingmachine.dto;

import com.jwade.vendingmachine.service.VendingMachineInsufficientFundsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author jwade
 */
public class Change {
    private final HashMap<Coins, Integer> coinChangeMap = new HashMap<>();

    public Change() {
    }

    public HashMap<Coins, Integer> getChange(BigDecimal funds) throws VendingMachineInsufficientFundsException {

        List<Coins> coins = Arrays.asList(Coins.QUARTERS, Coins.DIME, Coins.NICKLE, Coins.PENNY);


        for (Coins coin : coins) {

            int num = funds.divide(coin.getValue(), 0, RoundingMode.DOWN).intValue();
            funds = funds.remainder(coin.getValue());
            coinChangeMap.put(coin, num);
        }

        return coinChangeMap;
    }

}
