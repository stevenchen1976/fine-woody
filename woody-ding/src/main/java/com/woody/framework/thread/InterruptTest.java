package com.woody.framework.thread;

import java.util.concurrent.TimeUnit;

public class InterruptTest {

    private static int count;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                count++;
            }
            System.out.println("count : " + count);
        });

        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();  //interrupt= true
    }
}
