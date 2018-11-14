package com.woody.framework.jvm;

public class B extends A {

    {
        System.out.println("E");
    }

//    B() {
//        System.out.println("H");
//    }

    static {
        System.out.println("F");
    }

    B(int t) {
        super(10);
        System.out.println("G");
    }


}
