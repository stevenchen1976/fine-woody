package com.woody.framework.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//AIOServer
public class AIOServer {
    private final int port;

    //注册一个端口，用来给客户端连接
    public AIOServer(int port) {
        this.port = port;
        listen();
    }

    public static void main(String args[]) {
        int port = 8000;
        new AIOServer(port);
    }

    //侦听方法
    private void listen() {
        try {
            //线程缓冲池，为了体现异步
            ExecutorService executorService = Executors.newCachedThreadPool();
            //给线程池初始化一个线程
            AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            //Asynchronous异步
            //NIO   ServerSocketChannel
            //BIO   ServerSocket   有那么一点点像
            //同样的，也是先把高速公路修通
            final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(threadGroup);
            //打开高速公路的关卡
            server.bind(new InetSocketAddress(port));
            System.out.println("服务已启动，监听端口" + port);
            //开始等待客户端连接
            //实现一个CompletionHandler 的接口，匿名的实现类
            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

                final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    //只要拿数据，捡现成的,我们都是懒人，IO操作都不用关心了
                    System.out.println("IO 操作成功，开始获取数据");
                    try {
                        buffer.clear();
                        result.read(buffer).get();
                        buffer.flip();
                        result.write(buffer);
                        buffer.flip();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    } finally {
                        try {
                            result.close();
                            server.accept(null, this);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                    System.out.println("操作完成");
                }


                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("IO 操作是失败: " + exc);
                }
            });
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}