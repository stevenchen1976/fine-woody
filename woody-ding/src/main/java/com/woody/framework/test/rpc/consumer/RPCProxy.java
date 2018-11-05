package com.woody.framework.test.rpc.consumer;

import com.woody.framework.test.rpc.core.InvokeMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RPCProxy {

    public static <T> T create(Class<?> clazz) {

        MethodProxy methodProxy = new MethodProxy(clazz);

//        T result = (T)Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), methodProxy);
        T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, methodProxy);

        return result;
    }
}

class MethodProxy implements InvocationHandler {

    private Class<?> clazz;

    public MethodProxy(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        if(proxy.getClass().isInterface()) {
        if (Object.class.equals(method.getDeclaringClass())) {
//            return method.invoke(proxy, args);
            return method.invoke(this, args);
        } else {
            return rpcInvoke(proxy, method, args);
        }
    }

    public Object rpcInvoke(Object proxy, Method method, Object[] args) {
        InvokeMsg msg = new InvokeMsg();
        msg.setClassName(this.clazz.getName());
        msg.setMethodName(method.getName());
        msg.setParam(method.getParameterTypes());
        msg.setValue(args);

        final ConsumerHandler consumerHandler = new ConsumerHandler();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encoder", new ObjectEncoder());
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

                            pipeline.addLast("handler", consumerHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect("localhost", 8086).sync();
//            future.channel().writeAndFlush(msg);
            future.channel().writeAndFlush(msg).sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

        return consumerHandler.getResponse();
    }


}


