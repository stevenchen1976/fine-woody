package com.woody.framework.netty.my.rpc.consumer.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RPCProxyHandler extends ChannelInboundHandlerAdapter {


    private Object response;

    public Object getResponse() {
        return response;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = msg;
        System.out.println("Client 接收到服务器端返回的消息 " + msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exception is general");
        super.exceptionCaught(ctx, cause);
    }
}
