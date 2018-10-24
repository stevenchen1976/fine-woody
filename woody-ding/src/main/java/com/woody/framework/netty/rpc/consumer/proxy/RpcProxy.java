package com.woody.framework.netty.rpc.consumer.proxy;

import com.woody.framework.netty.rpc.core.msg.InvokerMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
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

public class RpcProxy {  
	
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<?> clazz){        
        MethodProxy methodProxy = new MethodProxy(clazz);        
		T result = (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[] { clazz },methodProxy); 
        return result;
    }
}


class MethodProxy implements InvocationHandler {
	private Class<?> clazz;
	public MethodProxy(Class<?> clazz){
		this.clazz = clazz;
	}
	//代理，调用IRpcHello接口中每一个方法的时候，实际上就是发起一次网络请求
	@Override
    public Object invoke(Object proxy, Method method, Object[] args)  throws Throwable {        
        //如果传进来是一个已实现的具体类（本次演示略过此逻辑)
        if (Object.class.equals(method.getDeclaringClass())) {  
            try {  
                return method.invoke(this, args);  
            } catch (Throwable t) {  
                t.printStackTrace();  
            }  
        //如果传进来的是一个接口（核心)
        } else {  
            return rpcInvoke(proxy,method, args);  
        }  
        return null;
    }
	
	
    /**
     * 实现接口的核心方法 
     * @param method
     * @param args
     * @return
     */
    public Object rpcInvoke(Object proxy,Method method,Object[] args){  
    	  InvokerMsg msg = new InvokerMsg();
	      msg.setClassName(this.clazz.getName());  
	      msg.setMethodName(method.getName());
	      msg.setValues(args);  
	      msg.setParames(method.getParameterTypes());  
	  	
	      final RpcProxyHandler consumerHandler = new RpcProxyHandler();
	      //（指定NIO类型，处理客户端事件）
		// 为进行事件处理分配了一个 NioEventLoopGroup 实例，
		// 其中事件处理包括创建新的 连接以及处理入站和出站数据
	      EventLoopGroup group = new NioEventLoopGroup();
	      try {
			  //为初始化客户端，创建了一个 Bootstrap 实例
	          Bootstrap b = new Bootstrap();
	          b.group(group)    
	           .channel(NioSocketChannel.class)
	           .option(ChannelOption.TCP_NODELAY, true)
			   //创建Channel时，向ChannelPipeline中添加一个RpcProxyHandler实例
	           .handler(new ChannelInitializer<SocketChannel>() {
	               @Override    
	               public void initChannel(SocketChannel ch) throws Exception {
					   ChannelPipeline pipeline = ch.pipeline();
	                   pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
	                   pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
	                   pipeline.addLast("encoder", new ObjectEncoder());
	                   pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
					   //当连接被建立时，一个 consumerHandler 实例会被安装到(该 Channel 的)ChannelPipeline 中
	                   pipeline.addLast("handler",consumerHandler);  
	               }
	           });    
	  
	          //连接到远程节点，阻塞等待直到连接完成
	          ChannelFuture future = b.connect("localhost", 8080).sync();
	          future.channel().writeAndFlush(msg).sync();
	          //阻塞，直到Channel关闭
	          future.channel().closeFuture().sync();    
	      } catch(Exception e){
	      	e.printStackTrace();
	      }finally {    
	          group.shutdownGracefully();    
	      }  
	      return consumerHandler.getResponse(); 
    } 

}
