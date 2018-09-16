package com.woody.framework.thread;

import java.util.concurrent.TimeUnit;

public class VolatileTest {

    private static volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        int count = 0;
        Thread thread = new Thread(()->{
            while(!stop) {
                System.out.println("Hi");
            }
        });

        thread.start();
        TimeUnit.SECONDS.sleep(2);
        stop=true;
    }

}
