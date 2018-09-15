package com.woody.framework.strategy;

public class PaymentMethodFactory {

    public static PaymentMethod getPaymentMethod(String type) {
        switch (type) {
            case "credit":
                return new CreditCard();
            case "debit":
                return new DebitCard();
            default:
                throw new RuntimeException("未实现支付方式");
        }
    }
}
