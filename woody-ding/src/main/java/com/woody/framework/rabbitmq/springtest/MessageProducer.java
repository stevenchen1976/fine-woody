package com.woody.framework.rabbitmq.springtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    @Qualifier("amqpTemplate")
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Object msg) {
        logger.error("send message :" + msg);

        amqpTemplate.convertAndSend("FirstKey", "[Direct,FirstKey]" + msg);

    }


}
