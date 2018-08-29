package com.woody.framework.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConfigUitl {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUitl.class);
    private static Properties configProperties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            configProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/config/config.properties"));
        } catch (Exception e) {
            logger.error("配置文件重载失败！");
        }
    }

    public static String getValue(String key) {
        return configProperties.getProperty(key);
    }


    /**
     * redis配置
     * @return
     */
    public static String getRedisHost() {
        return configProperties.getProperty("redis.host");
    }
    public static Integer getRedisPort() {
        return Integer.valueOf(configProperties.getProperty("redis.port"));
    }
    public static String getRedisPw() {
        return configProperties.getProperty("redis.pass");
    }
    public static Integer getRedisTimeOut() {
        return Integer.valueOf(configProperties.getProperty("redis.timeout"));
    }

    public static String getUploadDir() {

        return configProperties.getProperty("upload.dir");
    }
}
