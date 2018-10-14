package com.woody.framework.netty.rpc.provider;


import com.woody.framework.netty.rpc.api.IRpcHello;

public class RpcHello implements IRpcHello {
    
    @Override  
    public String hello(String name) {  
        return "Hello " + name + "!";  
    }  
  
}  
