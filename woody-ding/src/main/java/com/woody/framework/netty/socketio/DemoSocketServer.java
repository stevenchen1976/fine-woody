package com.woody.framework.netty.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import io.socket.client.Socket;

/**
 * 服务端的主要工作，就是添加各种事件的监听，然后在监听处理中，做相应的处理即可
 */
public class DemoSocketServer {


    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);

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

        //添加事件监听时，如果重复添加监听，会导致事件被处理多次，所以最好在添加事件监听前，先移除之前已经存在的监听
        //chat1namespace.removeAllListeners(Socket.EVENT_MESSAGE);
        //chat1namespace.addEventListener(Socket.EVENT_MESSAGE, String.class,...
        server.addEventListener(Socket.EVENT_MESSAGE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
                System.out.println("client data:" + data);
                server.getBroadcastOperations().sendEvent(Socket.EVENT_MESSAGE, "hi");
            }
        });

        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }

}