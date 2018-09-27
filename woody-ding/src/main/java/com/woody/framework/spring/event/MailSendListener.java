package com.woody.framework.spring.event;

import org.springframework.context.ApplicationListener;

public class MailSendListener implements ApplicationListener<MailSendEvent> {

    @Override
    public void onApplicationEvent(MailSendEvent event) {
        System.out.println("Hi" + event.getMsg());
    }
}
