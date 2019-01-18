package com.woody.framework.rabbitmq.delay;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.woody.framework.rabbitmq.RabbitMQUtil;

import java.io.IOException;

public class Producter {

    private static String QUEUE_NAME = "DELAY_QUEUE";
    private static String Message = "This is a test message!";

    public static void main(String[] args) throws IOException {

        Connection rabbitMQConnection = RabbitMQUtil.getRabbitMQConnection();
        Channel channel = rabbitMQConnection.createChannel();
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();

        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", QUEUE_NAME, properties, Message.getBytes());
        }
    }
}
