package com.woody.framework.gptomcat.nettyserver;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.apache.log4j.Logger;

public class GPTomcat {

    private static Logger LOG = Logger.getLogger(GPTomcat.class);

    public static void main(String[] args) {
        try {
            new GPTomcat().start(8081);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(int port) throws InterruptedException {

        //主从
        //Boos线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //Worker线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //Netty服务
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    //主线程处理类
                    .channel(NioServerSocketChannel.class)
                    //子线程的处理，Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {
                            //无锁化串行编程
                            //业务逻辑链路，编码器
                            //服务端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                            client.pipeline().addLast(new HttpResponseEncoder());
                            //服务端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                            client.pipeline().addLast(new HttpRequestDecoder());
                            //最后处理自己的逻辑
                            client.pipeline().addLast(new GPTomcatHandler());
                        }
                    })
                    //配置信息
                    .option(ChannelOption.SO_BACKLOG, 128)//针对主线程的配置，分配线程最多128
                    .childOption(ChannelOption.SO_KEEPALIVE, true); //子线程的配置, 长连接服务

            //绑定服务端口
            ChannelFuture f = server.bind(port).sync(); //阻塞

            System.out.println("HTTP服务已启动，监听端口:" + port);
            LOG.info("HTTP服务已启动，监听端口:" + port);

            //开始接收客户
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
