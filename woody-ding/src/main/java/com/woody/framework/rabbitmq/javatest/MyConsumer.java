package com.woody.framework.rabbitmq.javatest;

import com.rabbitmq.client.*;
import com.woody.framework.rabbitmq.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MyConsumer {

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
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Waiting for message ...");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received message :" + msg );
            }
        } ;
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
