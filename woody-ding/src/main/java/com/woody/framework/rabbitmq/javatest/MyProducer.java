package com.woody.framework.rabbitmq.javatest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.woody.framework.rabbitmq.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MyProducer {

    private final static String QUEUE_NAME = "ORIGN_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection mqConnection = RabbitMQUtil.getRabbitMQConnection();
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("192.168.55.121");
//        factory.setPort(5672);
//        factory.setVirtualHost("/");
//        factory.setUsername("vagrant");
//        factory.setPassword("vagrant");
//        Connection mqConnection = factory.newConnection();

        Channel channel = mqConnection.createChannel();
        String msg = "hello world, Rabbit MQ";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        channel.close();
        mqConnection.close();
    }
}
