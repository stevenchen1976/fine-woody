package com.woody.framework.jvm;

public class A {

    {
        System.out.println("A");
    }

    static {
        System.out.println("B");
    }

    A() {
        this(10);
        System.out.println("C");
    }

    A(int a) {
        System.out.println("D");
    }
}
