package com.woody.framework.redis;

public class RedisLockTest extends Thread {

    @Override
    public void run() {
        while (true) {
            DistributeRedisLock redisLock = new DistributeRedisLock();
            String rs = redisLock.acquireLock("updateOrder", 2000, 5000);
            if(rs != null) {
                System.out.println(Thread.currentThread().getName() + "->成功获得锁：" + rs);
                try {
                    Thread.sleep(1000);
//                    redisLock.releaseLock("updateOrder", rs);
                    redisLock.releaseLockWithLua("updateOrder", rs);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
        }
    }

    public static void main(String[] args){
        RedisLockTest redisLockTest = new RedisLockTest();
        for(int i=0; i < 10; i++) {
            new Thread(redisLockTest, "tName" + i).start();
        }
    }
}
