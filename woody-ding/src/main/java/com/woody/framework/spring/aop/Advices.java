package com.woody.framework.spring.aop;

import org.aspectj.lang.JoinPoint;

/**
 * 通知类
 */
public class Advices {

    public void before(JoinPoint jp){
        System.out.println("----------before advice----------");
        System.out.println(jp.getSignature().getName());
    }

    public void after(JoinPoint jp){
        System.out.println("----------after advice----------");
    }

}
