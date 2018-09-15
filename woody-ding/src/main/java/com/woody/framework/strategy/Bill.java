package com.woody.framework.strategy;

import java.util.ArrayList;
import java.util.List;

public class Bill {

    private List<Item> listItem = new ArrayList<>();

    public void addItem(Item item) {
        listItem.add(item);
    }

    public void remove(Item item) {
        listItem.remove(item);
    }

    public int getSumCents() {
        return listItem.stream().mapToInt(listItem->listItem.getCents()).sum();
    }

    public void pay(PaymentMethod paymentMethod) {
        paymentMethod.pay(getSumCents());
    }
}
