package com.woody.framework.redis;

import com.aliyun.oss.ServiceException;
import com.woody.framework.utils.ConfigUitl;
import org.redisson.client.RedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 获取redis连接
 */
public class RedisManager {

    private static JedisPool jedisPool = null;
     static {
         JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
         jedisPoolConfig.setMaxTotal(10);
         jedisPoolConfig.setMaxIdle(20);

         jedisPool = new JedisPool(jedisPoolConfig,ConfigUitl.getRedisHost(),ConfigUitl.getRedisPort(),ConfigUitl.getRedisTimeOut(), ConfigUitl.getRedisPw());
     }

     public static Jedis getJedisPool() throws Exception{
         if(jedisPool != null) {
             return jedisPool.getResource();
         } else {
             throw new Exception("JedisPool was not init!");
         }
     }

}
