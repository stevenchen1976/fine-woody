package com.woody.framework.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class RabbitMQUtil {

    private static Connection conn;

    static {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.55.121");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("vagrant");
        factory.setPassword("vagrant");

        try {
            conn = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getRabbitMQConnection() {
        return conn;
    }
}
