package com.woody.framework.spring.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;


public class MailSendEvent extends ApplicationContextEvent {
    private String msg;

    /**
     * Create a new ContextStartedEvent.
     *
     * @param source the {@code ApplicationContext} that the event is raised for
     *               (must not be {@code null})
     */
    public MailSendEvent(ApplicationContext source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {

        return msg;
    }
}
