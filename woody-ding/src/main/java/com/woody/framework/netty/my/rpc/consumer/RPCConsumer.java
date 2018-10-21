package com.woody.framework.netty.my.rpc.consumer;

import com.woody.framework.netty.my.rpc.api.Hello;
import com.woody.framework.netty.my.rpc.consumer.proxy.RPCProxy;

public class RPCConsumer {

    public static void main(String[] args) {
        Hello rpcHello = RPCProxy.create(Hello.class);
        System.out.println(rpcHello.sayHello("Woody"));
    }
}
