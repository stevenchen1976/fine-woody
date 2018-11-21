package com.woody.framework.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class PiplelineDemo {

    public static void main(String[] args) throws Exception {
      Jedis jedis = RedisUtil.getJedisPool();
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("aa", "11");
        pipeline.set("bb", "22");
        pipeline.set("cc", "11");
        pipeline.set("dd", "11");
        pipeline.sync();
    }

}
