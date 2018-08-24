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
}
