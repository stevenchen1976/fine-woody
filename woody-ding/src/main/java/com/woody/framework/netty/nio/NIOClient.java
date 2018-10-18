package com.woody.framework.netty.nio;

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

public class NIOClient {

    private static String USER_EXITS = "系统提示：该昵称已存在，请重新换个昵称！";
    private static String USER_CONTENT_SPILT = "#@#";
    private final InetSocketAddress serverAddress = new InetSocketAddress("localhost", 8081);
    private Selector selector;
    private SocketChannel client;
    private String nickname = "";
    private Charset charset = Charset.forName("UTF-8");

    public NIOClient() throws IOException {
        //先修好路，把关卡放开
        //连接远程主机的IP和端口
        client = SocketChannel.open(serverAddress);
        client.configureBlocking(false);

        //开门接待客人
        selector = Selector.open();
        client.register(selector, SelectionKey.OP_READ);

    }

    public static void main(String[] args) throws IOException {
        new NIOClient().session();
    }

    public void session() {
        //开辟一个新的线程从服务器端读取数据
        new Reader().start();
        //开辟一个新的线程向服务器端写入数据
        new Writer().start();
    }

    public class Writer extends Thread {

        @Override
        public void run() {
            try {
                //在主线程中，从键盘读取数据输入到服务器端
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if ("".equals(line)) {
                        continue;  //不允许发送空消息
                    }
                    if ("".equals(nickname)) {
                        nickname = line;
                        line = nickname + USER_CONTENT_SPILT;
                    } else {
                        line = nickname + USER_CONTENT_SPILT + line;
                    }
                    //client既能写，也能读
                    //向通道中写入数据
                    client.write(charset.encode(line));
                }
                scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class Reader extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) {
                        continue;
                    }
                    //获取通道的集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectionKeys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        process(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void process(SelectionKey key) throws IOException {
            if (key.isReadable()) {
                //client既能写，也能读，这边是读
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                String content = "";
                while (channel.read(byteBuffer) > 0) {
                    byteBuffer.flip();
                    content += charset.decode(byteBuffer);
                }
                //若系统发送通知名字已经存在，则需要换个昵称
                if (USER_EXITS.equals(content)) {
                    nickname = "";
                }
                System.out.println("Hi" + content);
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }

}
