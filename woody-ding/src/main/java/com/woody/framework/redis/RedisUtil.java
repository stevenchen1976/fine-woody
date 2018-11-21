package com.woody.framework.redis;

import com.woody.framework.utils.ConfigUitl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 获取redis连接
 */
public class RedisUtil {

    private static JedisPool jedisPool = null;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(20);

//        jedisPool = new JedisPool(jedisPoolConfig, ConfigUitl.getRedisHost(), ConfigUitl.getRedisPort(), ConfigUitl.getRedisTimeOut(), ConfigUitl.getRedisPw());
        jedisPool = new JedisPool(jedisPoolConfig,
                "10.211.55.6",
                6379,
                5000);
    }

    public static Jedis getJedisPool() throws Exception {
        if (jedisPool != null) {
            return jedisPool.getResource();
        } else {
            throw new Exception("JedisPool was not init!");
        }
    }
}
