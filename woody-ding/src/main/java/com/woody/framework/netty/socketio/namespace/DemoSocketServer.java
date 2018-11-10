package com.woody.framework.netty.socketio.namespace;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import io.socket.client.Socket;

public class DemoSocketServer {

    public static void main(String[] args) throws InterruptedException {
        SocketIOServer server = getServer();
        addRoom(server);
        startServer(server);
    }

    private static Configuration getConfig() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        return config;
    }

    private static void handleConn(SocketIOServer server) {
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                String token = client.getHandshakeData().getUrlParams().get("token").get(0);
                if (!token.equals("87df42a424c48313ef6063e6a5c63297")) {
                    client.disconnect();//校验token示例
                }
                System.out.println("sessionId:" + client.getSessionId() + ",token:" + token);
            }
        });
    }

    private static void addRoom(SocketIOServer server) {
        final SocketIONamespace chat1namespace = server.addNamespace("/room1");
        chat1namespace.addEventListener(Socket.EVENT_MESSAGE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                chat1namespace.getBroadcastOperations().sendEvent(Socket.EVENT_MESSAGE, "ack:" + data);
            }
        });
    }

    private static SocketIOServer getServer() throws InterruptedException {
        final SocketIOServer server = new SocketIOServer(getConfig());
        handleConn(server);

        server.addEventListener(Socket.EVENT_MESSAGE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
                System.out.println("client data:" + data);
                server.getBroadcastOperations().sendEvent(Socket.EVENT_MESSAGE, "hi");
            }
        });
        return server;
    }

    private static void startServer(SocketIOServer server) throws InterruptedException {
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }
}
