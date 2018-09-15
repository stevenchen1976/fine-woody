package com.woody.framework.strategy;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public abstract class Card implements PaymentMethod{

    static Map<String, PaymentMethod> payMap = new HashMap<>();

    @PostConstruct
    public void init() {
        payMap.put(getType(), this);
    }

    @Override
    public void pay(int cents) {
        System.out.println("use " + getType() + " payed " + cents + "cents");
        executeTransaction(cents);  //具体执行支付操作
    }

    protected abstract String getType();

    protected abstract void executeTransaction(int cents);
}
