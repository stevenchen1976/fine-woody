package com.woody.framework.strategy;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args){
        Bill bill = new Bill();
        bill.addItem(new Item("JVM实战",100));
        bill.addItem(new Item("SpringBoot编程思想",200));
//        bill.pay(PaymentMethodFactory.getPaymentMethod("debit"));
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/application-context.xml");
        context.start();
        bill.pay(Card.payMap.get("debit"));
    }
}
