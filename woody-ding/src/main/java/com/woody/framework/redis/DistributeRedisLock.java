package com.woody.framework.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.UUID;

public class DistributeRedisLock {

    /**
     * 获得锁
     *
     * @param lockName       锁的名字
     * @param acquireTimeout 尝试获取锁的时间
     * @param lockTimeout    锁本身的过期时间
     * @return
     */
    public String acquireLock(String lockName, long acquireTimeout, long lockTimeout) {
        //保证释放锁的时候是同一个锁持有锁的人
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;
        int lockExpire = (int) (lockTimeout / 1000);
        Jedis jedis = null;

        //尝试获取锁的时
        long end = System.currentTimeMillis() + acquireTimeout;
        try {
            jedis = RedisUtil.getJedisPool();

            //在尝试时间内尝试获取锁
            while (System.currentTimeMillis() < end) {
                //获取锁
                if (jedis.setnx(lockKey, identifier) == 1) {
                    //设置过期时间
                    jedis.expire(lockKey, lockExpire);
                    //获取锁成功
                    return identifier;
                }
                //如果锁没有设置超时时间，则设置超时时间
                if (jedis.ttl(lockKey) == -1) {
                    jedis.expire(lockKey, lockExpire); //设置过期时间
                }

                try {
                    //等待片刻尝试获取锁
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    //释放锁
    public boolean releaseLock(String lockName, String identifier) {
        System.out.println("开始释放锁：" + identifier);
        String lockKey = "lock:" + lockName;
        boolean isRelease = false;
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedisPool();
            while (true) {
                //如果其他地方修改了这个key，则下面操作失败
                //保证释放锁不被打断
                jedis.watch(lockKey);

                //判断是否为同一把锁
                if(identifier.equals(jedis.get(lockKey))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    if(transaction.exec().isEmpty()) {
                        continue;
                    }
                    isRelease = true;
                }
                //TODO 释放失败会抛出异常
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

        return isRelease;
    }

    //基于lua脚本释放锁，保证原子性
    public boolean releaseLockWithLua(String lockName, String identifier) {
        try {
            Jedis jedis = RedisUtil.getJedisPool();
            String lockKey = "lock:" + lockName;
            String lua = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then " +
                    "return redis.call(\"del\", KEYS[1]) " +
                    "else return 0 end";
            Long rs = (Long)jedis.eval(lua, 1, new String[]{lockKey, identifier});
//            jedis.evalsha();
            if(rs.intValue() > 0) {
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
