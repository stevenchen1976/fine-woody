package com.woody.framework.spring;

import com.woody.framework.spring.aop.AOPMath;
import com.woody.framework.spring.event.MailSender;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring/application-beans.xml");
        MailSender mailSender = (MailSender)ctx.getBean("mailSender");
        mailSender.sendMail();


        AOPMath math = ctx.getBean("math", AOPMath.class);
        int n1 = 10, n2 = 5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);

    }
}
