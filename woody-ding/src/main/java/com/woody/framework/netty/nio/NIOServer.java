package com.woody.framework.netty.nio;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NIOServer  {

    private Integer port;
    private Selector selector = null;

    //用来记录在线人数，以及昵称
    private Set<String> users = new HashSet<>();
    private static String USER_EXITS = "系统提示：该昵称已存在，请重新换个昵称！";
    //相当于自定义协议格式，与客户端协商好
    private static String USER_CONTENT_SPILT = "#@#";
    private Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) throws IOException {
        new NIOServer(8081).listener();
    }

    public NIOServer(Integer port) throws IOException {

        this.port = port;

        //要想富先修路，先把通道打开
        //创建可选择通道，并配置为非阻塞
        ServerSocketChannel server = ServerSocketChannel.open();

        //设置高速公路关卡
        //绑定通道到指定端口
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(this.port));

        //开门迎客，排队叫号大厅开始工作
        //创建Selector对象
        selector = Selector.open();

        //告诉服务大厅的工作人员，你可以开始待客了
        //向Selector中注册感兴趣事件
        //指定的是参数是 OP_ACCEPT，即指定想要监听 accept 事件，也就是新的连接发生时所产生的事件，
        //对于 ServerSocketChannel通道来说，唯一可以指定的参数就是 OP_ACCEPT
        server.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Hi, 服务已启动，监听的端口是" + port);
    }

    //从 Selector 中获取感兴趣的事件，即开始监听，进入内部循环:
    public void listener() throws IOException {
        try {
            //轮询（在非阻塞I/O中，内部循环模式基本上都是遵循这种方式）
            while (true) {
                //在轮询，服务大厅中有多少人排队
                int wait = selector.select();
                if(wait == 0) { //如果没有人排队，则进入下一次轮询
                    continue;
                }
                //获取发生事件的SelectionKey（获取可用通道的集合）
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey)it.next();
                    it.remove();
                    //根据不同的事件处理
                    process(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //根据不同的事件，编写相应的处理代码
    public void process(SelectionKey key) throws IOException {
        //判断客户端确定已经进入服务大厅并且已经可以实现交互了
        //接收请求
        if(key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel)key.channel();
            SocketChannel client = server.accept();
//            client.getRemoteAddress();
            //非阻塞模式
            client.configureBlocking(false);
            //注册选择器，并设置为读取模式，收到一个连接请求，然后起一个SocketChannel，并注册到Selector上，
            //之后这个连接的数据，就由这个SocketChannle来处理
            client.register(selector, SelectionKey.OP_READ);

            //将此对应的channel设置为准备接受其他客户端请求
            key.interestOps(SelectionKey.OP_ACCEPT);

            client.write(charset.encode("请输入你的昵称："));
        }
        //处理来自客户端的数据读取请求
        else if(key.isReadable()) {
            //返回该SelectionKey对应的Channel，其中有数据读取
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuffer content = new StringBuffer();
            try {
                //从通道读出数据到缓冲区
                while (client.read(buffer) > 0) {
                    buffer.flip();
                    content.append(charset.decode(buffer));
                }
                System.out.println("Hi，从IP地址为：" + client.getRemoteAddress() + "获取的信息为" + content);
                //将此对应的channel设置为准备下一次接受数据
                key.interestOps(SelectionKey.OP_READ);
            } catch (Exception e) {
                key.cancel();
                if(key.channel() != null) {
                    key.channel().close();
                }
            }

            if(content.length() > 0) {
                String[] arrayContent = content.toString().split(USER_CONTENT_SPILT);
                //注册用户
                if(arrayContent!= null && arrayContent.length == 1) {
                    String nickname = arrayContent[0];
                    if(users.contains(nickname)) {
                        client.write(charset.encode(USER_EXITS));
                    } else {
                        users.add(nickname);
                        int onlineCount = onlineCount();
                        String message = "欢迎" + nickname + "加入聊天室，当前在线人数为" + onlineCount;
                        broadcast(null, message);
                    }
                }
                //注册完了，发送消息
                else if(arrayContent != null && arrayContent.length > 1){
                    String nickname = arrayContent[0];
                    String message = content.substring(nickname.length() + USER_CONTENT_SPILT.length());
                    message = nickname + "说" + message;
                    if(users.contains(nickname)) {
                        broadcast(client, message);
                    }
                }
            }
        }
    }

    //TODO 要是能检测下线，就不用这么统计了
    public int onlineCount() {
        int oncount = 0;
        for(SelectionKey key : selector.keys()) {
            Channel target = key.channel();
            if(target instanceof SocketChannel) {
                oncount ++;
            }
        }
        return oncount;
    }

    public void broadcast(SocketChannel client, String message) throws IOException {
        //广播数据到所有的SocketChannel中
        for(SelectionKey key : selector.keys()) {
            SelectableChannel targetChannel = key.channel();
            //不回发给发送此内容的客户端
            if(targetChannel instanceof SocketChannel && targetChannel != client) {
                SocketChannel target = (SocketChannel)targetChannel;
                target.write(charset.encode(message));
            }
        }
    }

}
