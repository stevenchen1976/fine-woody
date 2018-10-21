package com.woody.framework.netty.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AIOClient {
    private final AsynchronousSocketChannel client;

    public AIOClient() throws Exception {
        //Asynchronous
        //BIO   Socket
        //NIO   SocketChannel
        //AIO   AsynchronousSocketChannel
        //先把高速公路修起来
        client = AsynchronousSocketChannel.open();
    }

    public static void main(String args[]) throws Exception {
        new AIOClient().connect("localhost", 8000);
    }

    public void connect(String host, int port) throws Exception {
        //开始发车，连上高速公路
        //Viod什么都不是
        //也是实现一个匿名的接口
        //这里只做写操作
        client.connect(new InetSocketAddress(host, port), null, new
                CompletionHandler<Void, Void>() {
                    @Override
                    public void completed(Void result, Void attachment) {
                        try {
                            client.write(ByteBuffer.wrap("这是一条测试数据 ".getBytes())).get();
//                            System.out.println("已发送至服务器");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {
                        exc.printStackTrace();
                    }
                });

        //下面这一段代码是只读数据
        final ByteBuffer bb = ByteBuffer.allocate(1024);
        client.read(bb, null, new CompletionHandler<Integer, Object>() {
                    //实现IO操作完成的方法
                    @Override
                    public void completed(Integer result, Object attachment) {
                        System.out.println("IO 操作完成" + result);
                        System.out.println("获取反馈结果" + new String(bb.array()));
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        exc.printStackTrace();
                    }
                }
        );
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
}

//71265825358498


