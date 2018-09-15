package com.woody.framework.strategy;

import org.springframework.stereotype.Service;

@Service
public class DebitCard extends Card {

    @Override
    protected String getType() {
        return "debit";
    }

    @Override
    protected void executeTransaction(int cents) {
        System.out.println("Hi, do transaction wth debit : " + cents);
    }
}
