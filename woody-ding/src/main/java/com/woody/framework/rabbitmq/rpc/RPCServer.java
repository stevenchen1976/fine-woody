package com.woody.framework.rabbitmq.rpc;

import com.rabbitmq.client.*;
import com.woody.framework.rabbitmq.RabbitMQUtil;

import java.io.IOException;

public class RPCServer {

    private final static String REQUEST_QUEUE_NAME="RPC_REQUEST";

    public static void main(String[] args) throws IOException {
        Connection rabbitMQConnection = RabbitMQUtil.getRabbitMQConnection();
        Channel channel = rabbitMQConnection.createChannel();
        channel.queueDeclare(REQUEST_QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               AMQP.BasicProperties replayProperties = new AMQP.BasicProperties().builder()
                       .correlationId(properties.getCorrelationId())
                       .build();
               String replayTo = properties.getReplyTo();
               String msg = new String(body, "UTF-8");
               if(msg != null) {
                   String result = String.valueOf(Math.pow(Integer.valueOf(msg), 2));
                   channel.basicPublish("", replayTo, replayProperties, result.getBytes());
                   channel.basicAck(envelope.getDeliveryTag(), false);
               }
            }
        };

        channel.basicConsume(REQUEST_QUEUE_NAME, true, consumer);
    }
}


