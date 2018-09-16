package com.woody.framework.thread;

public class ThreadLocalDemo {


    private static ThreadLocal<Integer> num = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return 0;   //初始化值
        }
    };

    public static void main(String[] args){
        for(int i = 0; i < 5; i++) {
            new Thread(()->{
                Integer localNum = num.get().intValue() + 5;
                System.out.println(Thread.currentThread().getName() + "->" + localNum);
            },"t" + i).start();
        }
    }
}
