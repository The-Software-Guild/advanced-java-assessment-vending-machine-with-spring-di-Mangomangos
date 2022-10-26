package com.jwade.vendingmachine.dto;

import com.jwade.vendingmachine.service.VendingMachineInsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ChangeTest {

    Change testChange;

    @BeforeEach
    public void setUp () {
        testChange = new Change();
    }

    @Test
    @DisplayName("Testing getting a dollar in change")
    void getChangeTest(){

        HashMap<Coins, Integer> coinMap = null;
        try {
            coinMap = testChange.getChange(BigDecimal.ONE);
        } catch (VendingMachineInsufficientFundsException e) {
            throw new RuntimeException(e);
        }

        HashMap<Coins, Integer> expectedMap = new HashMap<>();
        expectedMap.put(Coins.QUARTERS, 4);
        expectedMap.put(Coins.DIME, 0);
        expectedMap.put(Coins.NICKLE, 0);
        expectedMap.put(Coins.PENNY, 0);

        assertEquals(expectedMap, coinMap);

    }


}