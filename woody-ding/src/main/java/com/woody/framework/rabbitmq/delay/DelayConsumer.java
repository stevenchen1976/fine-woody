package com.woody.framework.rabbitmq.delay;

import com.rabbitmq.client.*;
import com.woody.framework.rabbitmq.RabbitMQUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DelayConsumer {

    private static String QUEUE_NAME = "DELAY_QUEUE";

    public static void main(String[] args) throws IOException {

        Connection rabbitMQConnection = RabbitMQUtil.getRabbitMQConnection();
        Channel channel = rabbitMQConnection.createChannel();

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-dead-letter-exchange", "DLX_EXCHANGE");

        channel.queueDeclare(QUEUE_NAME, false, false, false, arguments);
        channel.exchangeDeclare("DLX_EXCHANGE", "topic", false, false, false, null);
        channel.queueDeclare("DTL_QUEUE", false, false, false, null);
        channel.queueBind("DTL_QUEUE", "DLX_EXCHANGE", "#");
        System.out.println(" Waiting for message....");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String receiveMsg = new String(body, "utf-8");
                System.out.println("Received msg : " + receiveMsg);
            }
        };

//        channel.basicConsume(QUEUE_NAME, true, consumer);
        channel.basicConsume("DTL_QUEUE", true, consumer);

    }
}
