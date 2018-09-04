package com.woody.framework.proxy.jdkproxy;

public class JdkSon implements Person {

    @Override
    public void findJob() {
        System.out.println("Hi, Son find Job!");
    }

    @Override
    public void findLove() {
        System.out.println("Hi, son find love");
    }
}
