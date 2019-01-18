package com.woody.framework.rabbitmq.springtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.UnsupportedEncodingException;

public class FirstConsumer implements MessageListener {


    private Logger logger = LoggerFactory.getLogger(FirstConsumer.class);

    @Override
    public void onMessage(Message message) {
        try {
            logger.info("The first consumer received msg : " + new String(message.getBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("",e);
        }
    }
}
