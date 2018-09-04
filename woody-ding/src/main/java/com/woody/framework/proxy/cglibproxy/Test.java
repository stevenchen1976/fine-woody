package com.woody.framework.proxy.cglibproxy;

public class Test {

    public static void main(String[] args){
        Dog dogCglib = (Dog)new CglibProxy().getInstance(Dog.class);
        dogCglib.eat();
        dogCglib.run();
    }
}
