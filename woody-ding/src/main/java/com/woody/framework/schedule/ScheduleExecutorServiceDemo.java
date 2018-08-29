package com.woody.framework.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorServiceDemo {

    public static void main(String[] args){
        ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(2);

        scheduledService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hi-----111" + System.currentTimeMillis());
            }
        },3000,5000,TimeUnit.MILLISECONDS);


        scheduledService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hi-----2222" + System.currentTimeMillis());
            }
        },3000,5000,TimeUnit.MILLISECONDS);

    }

}
