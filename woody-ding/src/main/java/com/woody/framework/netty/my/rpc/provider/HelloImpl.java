package com.woody.framework.netty.my.rpc.provider;

import com.woody.framework.netty.my.rpc.api.Hello;

public class HelloImpl implements Hello {

    @Override
    public String sayHello(String name) {
        return "hello, " + name;
    }
}
