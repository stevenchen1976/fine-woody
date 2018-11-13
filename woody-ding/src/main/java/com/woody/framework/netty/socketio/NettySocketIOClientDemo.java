package com.woody.framework.netty.socketio;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

public class NettySocketIOClientDemo {

    public static void main(String[] args) throws URISyntaxException {
        //客户端配置
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"};
        options.timeout = 5000;
        options.reconnectionAttempts = 50;
        options.reconnectionDelay = 500;

        //连接服务端
        Socket socket = IO.socket("http://127.0.0.1:8091?token=1323sdfsfsdsdfsaf13");
//        Socket socket = IO.socket("http://127.0.0.1:8091?/chat1?token=1323sdfsfsdsdfsaf13");

        //监听消息
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                socket.send("来自客户端连接的问候");
            }
        });
        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("连接关闭");
            }
        });

        socket.on("ServerEvent", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                for (Object obj : objects) {
                    System.out.println(obj.toString());
                }
            }
        });

        socket.emit("ClientEventAck", "ClientEventAckMsg", new Ack() {
            @Override
            public void call(Object... args) {
                for (Object obj : args) {
                    System.out.println(obj.toString());
                }
            }
        });

        socket.on("eventServerAck", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                for (Object obj : args) {
                    System.out.println("eventServerAck------" + obj.toString());
                }
                Ack ack = (Ack) args[args.length - 1];
                ack.call("this msg is eventServerAck client acked");
            }
        });


        //连接服务器
        socket.connect();
//        socket.disconnect();
    }

}
