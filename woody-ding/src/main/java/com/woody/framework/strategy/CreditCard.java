package com.woody.framework.strategy;

import org.springframework.stereotype.Service;

@Service
public class CreditCard extends Card {
    @Override
    protected String getType() {
        return "credit";
    }

    @Override
    protected void executeTransaction(int cents) {
        System.out.println("Hi, do transaction wth credit : " + cents);
    }
}
