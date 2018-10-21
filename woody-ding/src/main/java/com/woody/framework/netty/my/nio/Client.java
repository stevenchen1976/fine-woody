package com.woody.framework.netty.my.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Client {

    //客户端连接服务器端
    private InetSocketAddress serverAddress = new InetSocketAddress("localhost", 8082);
    private Selector selector;
    private Charset charset = Charset.forName("UTF-8");
    private SocketChannel client;     //当前客户端

    public static void main(String[] args) throws IOException {
       new Client().session();
    }

    //启动客户端，连接服务器
    public Client() throws IOException {

        client = SocketChannel.open(serverAddress);
        client.configureBlocking(false);

        selector = selector.open();

        //注册可读事件，接收服务端发送给自己的的消息
        client.register(selector, SelectionKey.OP_READ);
    }

    //客户端开始读写线程
    public void session() {
        new Reader().start();
        new Writer().start();
    }

    //客户端读操作（读是"被动的"，需要轮询有谁给自己发送消息）
    public class Reader extends Thread {

        @Override
        public void run() {
            try {
                //轮询
                while (true) {
                    int wait = selector.select();
                    if (wait == 0) {
                        continue;
                    }

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        keyIterator.remove();
                        process(key);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void process(SelectionKey key) throws IOException {
            //读取服务器端发送过来的数据
            if (key.isReadable()) {
                SocketChannel client = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                StringBuilder msg = new StringBuilder();
                while (client.read(buffer) > 0) {
                    buffer.flip();
                    msg.append(charset.decode(buffer));
                }

                System.out.println("收到服务器的信息为：" + msg);
            }
        }
    }

    //客户端写操作（写是"主动"的）
    public class Writer extends Thread {
        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                try {
                    client.write(charset.encode(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            scanner.close();
        }
    }


}
