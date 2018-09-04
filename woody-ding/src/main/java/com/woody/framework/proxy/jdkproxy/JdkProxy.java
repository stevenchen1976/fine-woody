package com.woody.framework.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy implements InvocationHandler {

    private JdkSon target;

    public  Object getInstance(JdkSon target) {
        this.target = target;
        Class<? extends JdkSon> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdkProxy before....");
        method.invoke(this.target, args);
        System.out.println("jdkProxy after");
        return null;
    }
}
