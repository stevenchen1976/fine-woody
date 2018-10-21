package com.woody.framework.netty.nio.my;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private Integer port;
    private Charset charset = Charset.forName("UTF-8");
    private Selector selector;

    public static void main(String[] args) throws IOException {
       new Server(8082).listener();
    }

    //启动服务端
    public Server(Integer port) throws IOException {
        this.port = port;

        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(port));

        selector = Selector.open();

        server.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务端已启动，监听端口：" + this.port);
    }

    //监听事件
    public void listener() {
        try {
            //轮询
            while (true) {
                int wait = selector.select();
                if(wait == 0) {
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
                    //处理事件
                    process(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //处理事件
    private void process(SelectionKey key) throws IOException {
        if(key.isAcceptable()) {
            ServerSocketChannel server =(ServerSocketChannel)key.channel();
            SocketChannel client = server.accept();

            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);

            client.write(charset.encode("已连接到服务器，请输入昵称："));

        } else if(key.isReadable()) {
            try {
                //服务器端从通道中读取信息到缓存
                SocketChannel client = (SocketChannel)key.channel();
                StringBuilder msg = new StringBuilder();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (client.read(buffer) > 0) {
                    buffer.flip();
                    msg.append(charset.decode(buffer));
                }
                //将此客户端发送的消息，通过服务器器端广播给其他客户端
                broadcast(client, msg.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //服务器广播消息给客户端
    private void broadcast(SocketChannel client, String msg) throws IOException {
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            SelectableChannel channel = key.channel();
            if(channel instanceof SocketChannel && channel != client) {
                SocketChannel targetChannel = (SocketChannel)channel;
                targetChannel.write(charset.encode(msg));
            }
        }
    }
}
