package com.woody.framework.redis;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis模版-Object值类型
 */
public class ObjectRedisTemplate extends RedisTemplate<String, Object> {

    public ObjectRedisTemplate() {
        JdkSerializationRedisSerializer objectSerializer = new JdkSerializationRedisSerializer();
        setKeySerializer(new StringRedisSerializer());
        setValueSerializer(objectSerializer);

        setHashKeySerializer(new StringRedisSerializer());
        setHashValueSerializer(objectSerializer);
    }

    public ObjectRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
