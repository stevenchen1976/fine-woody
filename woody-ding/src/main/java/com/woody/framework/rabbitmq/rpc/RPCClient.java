package com.woody.framework.rabbitmq.rpc;

import com.rabbitmq.client.*;
import com.woody.framework.rabbitmq.RabbitMQUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RPCClient {

    private final static String REQUEST_QUEUE_NAME = "RPC_REQUEST";
    private final static String RESPONSE_QUEUE_NAME = "RPC_RESPONSE";
    private static Channel channel;

    RPCClient() throws IOException {
        channel = RabbitMQUtil.getRabbitMQConnection().createChannel();
        channel.queueDeclare(REQUEST_QUEUE_NAME, true, false, false, null);
        channel.queueDeclare(RESPONSE_QUEUE_NAME, true, false, false, null);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        RPCClient client = new RPCClient();
        String result = getSquare("2");
        System.out.println("Hi!, result is " + result);
    }

    public static String getSquare(String num) throws IOException, InterruptedException {

        String correlationId = UUID.randomUUID().toString();
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .correlationId(correlationId)
                .replyTo(RESPONSE_QUEUE_NAME)
                .build();
        channel.basicPublish("", REQUEST_QUEUE_NAME, false, properties, num.getBytes());


        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                response.offer(new String(body, "UTF-8"));
            }
        };

        channel.basicConsume(RESPONSE_QUEUE_NAME, true, consumer);
        return response.take();
    }
}
