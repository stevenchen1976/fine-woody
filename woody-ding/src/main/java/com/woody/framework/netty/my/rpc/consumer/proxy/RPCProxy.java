package com.woody.framework.netty.my.rpc.consumer.proxy;

import com.woody.framework.netty.my.rpc.core.InvokeMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RPCProxy {

    public static <T> T create(Class<?> clazz) {
        MethodProxy methodProxy = new MethodProxy(clazz);

        T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, methodProxy);
        return result;
    }

}

class MethodProxy implements InvocationHandler {

    private Class<?> clazz;

    public MethodProxy(Class<?> clazz) {
        this.clazz = clazz;
    }

    //代理，调用接口中每一个方法的时候，实际上就是发起一次网络请求
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果传进来的是一个已实现的具体类（略过此逻辑）
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            //传进来的是接口
            return rpcInvoke(proxy, method, args);
        }
    }


    private Object rpcInvoke(Object proxy, Method method, Object[] args) {
        InvokeMsg msg = new InvokeMsg();
        msg.setClassName(this.clazz.getName());
        msg.setMethodName(method.getName());
        msg.setParams(method.getParameterTypes());
        msg.setValue(args);

        final RPCProxyHandler consumerHandler = new RPCProxyHandler();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast("encoder", new ObjectEncoder());
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast("handler", consumerHandler);
                        }
                    });

            ChannelFuture future = b.connect("localhost", 8085).sync();
            future.channel().writeAndFlush(msg).sync();
            future.channel().closeFuture().sync();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return consumerHandler.getResponse();
    }
}



