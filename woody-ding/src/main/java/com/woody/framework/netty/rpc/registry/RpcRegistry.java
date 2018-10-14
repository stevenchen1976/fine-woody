package com.woody.framework.netty.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class RpcRegistry {  
    private int port;  
    public RpcRegistry(int port){  
        this.port = port;  
    }  
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
          
        try {  
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            		.channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
  
                        @Override  
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //处理的拆包、粘包的解、编码器
//                          //TCP以流的方式进行数据传输，上层应用协议为了对消息进行区分，往往采用如下4种方式
                            //消息长度固定、回车换行符作为消息结束符、特殊分隔符作为消息结束符、通过在消息头中定义长度字段来标示消息的总长度
                             pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                pipeline.addLast(new LengthFieldPrepender(4));
                                //处理序列化的解、编码器（JDK的默认序列化）
                                pipeline.addLast("encoder", new ObjectEncoder());
                                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            //业务逻辑
                                pipeline.addLast(new RegistryHandler());   
                        }  
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = b.bind(port).sync();
            System.out.println("RPC Registry start listen at " + port );    
            future.channel().closeFuture().sync();    
        } catch (Exception e) {  
             bossGroup.shutdownGracefully();    
             workerGroup.shutdownGracefully();  
        }  
    }
    
    
    public static void main(String[] args) throws Exception {    
        new RpcRegistry(8080).start();    
    }    
}  
