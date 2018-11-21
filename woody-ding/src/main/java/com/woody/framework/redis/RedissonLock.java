package com.woody.framework.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedissonLock  {

    public static void main(String[] args){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://10.211.55.6:6379");
        RedissonClient redissonClient = Redisson.create(config);
        RLock rLock = redissonClient.getLock("updateOrder");
        try {
            rLock.tryLock(100, 10, TimeUnit.SECONDS);
            System.out.println("Redisson test");
            Thread.sleep(1000);
            rLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            redissonClient.shutdown();
        }
    }
}
