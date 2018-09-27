package com.woody.framework.spring.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

public class MailSender implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void sendMail() {
        System.out.println("Hi, 正在发送邮件");
        MailSendEvent ms = new MailSendEvent(this.applicationContext, "发给章三");
        applicationContext.publishEvent(ms);
        System.out.println(ms.getMsg());
    }
}
