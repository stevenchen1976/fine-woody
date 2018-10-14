package com.woody.framework.netty.rpc.consumer;

import com.woody.framework.netty.rpc.api.IRpcCalc;
import com.woody.framework.netty.rpc.api.IRpcHello;
import com.woody.framework.netty.rpc.consumer.proxy.RpcProxy;

public class RpcConsumer {
	
    public static void main(String [] args){
        //不能new，否则是本机一个人玩
        //动态代理实现，传给它一个接口，返回一个实例，伪代理
        IRpcHello rpcHello = RpcProxy.create(IRpcHello.class);
        
        System.out.println(rpcHello.hello("Tom老师"));
        
        
        IRpcCalc calc = RpcProxy.create(IRpcCalc.class);
        
        System.out.println("8 + 2 = " + calc.add(8, 2));
        System.out.println("8 - 2 = " + calc.sub(8, 2));
        System.out.println("8 * 2 = " + calc.mult(8, 2));
        System.out.println("8 / 2 = " + calc.div(8, 2));
    }
    
}
