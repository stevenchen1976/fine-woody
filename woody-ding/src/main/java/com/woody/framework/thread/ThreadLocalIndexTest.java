package com.woody.framework.thread;

public class ThreadLocalIndexTest {

    private static ThreadLocal<Index> indexLocal = new ThreadLocal<Index>(){
        protected Index initialValue() {
            return new Index();
        }
    };

    static class Index{
        int num;
        public void incre() {
            num++;
        }
    }
    public static void main(String[] args){
        for(int i = 0; i<5; i++) {
            new Thread(()->{
                Index index = indexLocal.get();
                index.incre();
                System.out.println(Thread.currentThread().getName() + "->" + indexLocal.get().num);
            },"thread" + i).start();
        }
    }
}
