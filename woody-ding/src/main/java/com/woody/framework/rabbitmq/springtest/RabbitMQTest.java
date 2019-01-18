package com.woody.framework.rabbitmq.springtest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RabbitMQTest {

    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("/spring/application-context.xml");
        MessageProducer producer = (MessageProducer) context.getBean("messageProducer");
        int k = 100;
        while (k > 0) {
            producer.sendMessage("第" + k + "次发送消息");
            k--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
