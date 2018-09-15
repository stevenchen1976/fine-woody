package com.woody.framework.strategy;

public class Item {

    private String desc;
    private int cents;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }

    public Item(String desc, int cents) {

        this.desc = desc;
        this.cents = cents;
    }
}
