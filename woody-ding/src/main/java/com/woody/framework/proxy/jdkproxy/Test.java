package com.woody.framework.proxy.jdkproxy;

public class Test {

    public static void main(String[] args){
        Person person = (Person)new JdkProxy().getInstance(new JdkSon());
        person.findJob();
        person.findLove();
    }
}
