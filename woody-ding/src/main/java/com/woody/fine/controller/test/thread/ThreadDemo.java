package com.woody.fine.controller.test.thread;

public class ThreadDemo extends Thread {

    private String threadName;

    public ThreadDemo(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Hi" + this.getName());
    }
}
