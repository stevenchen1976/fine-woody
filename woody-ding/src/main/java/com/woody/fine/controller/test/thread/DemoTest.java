package com.woody.fine.controller.test.thread;

public abstract class      DemoTest {


    abstract void test3();

    private void test() {

    }

    static void test1(){

    }

    public static void main(String[] args){
        ThreadDemo demo1 = new ThreadDemo("thread1");
        ThreadDemo demo2 = new ThreadDemo("thread2");
        demo1.start();
        demo2.start();
    }
}
