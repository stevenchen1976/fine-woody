package com.woody.framework.netty.socketio;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.woody.framework.utils.StringUtil;
import io.socket.client.Socket;

public class NettySocketIOServerDemo {

    public static void main(String[] args) throws InterruptedException {
        //配置连接信息
        Configuration configuration = new Configuration();
        configuration.setHostname("127.0.0.1");
        configuration.setPort(8091);

        //初始化服务器
        SocketIOServer server = new SocketIOServer(configuration);

        //连接监听
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                String token = client.getHandshakeData().getUrlParams().get("token").get(0);
                if (!StringUtil.isNullOrBlank(token) && "1323sdfsfsdsdfsaf13".equals(token)) {
                    System.out.println("client sid :" + client.getSessionId() + " 成功连接服务器");
                } else {
                    client.disconnect();
                }

                //发送回调事件
                Object ackChatObjectData = new Object();
                client.sendEvent("eventServerAck", new AckCallback<String>(String.class) {
                    //客户端响应完成后执行
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getSessionId() + " data: " + result);
                    }
                }, ackChatObjectData);

            }
        });

        //监听事件
        server.addEventListener(io.socket.engineio.client.Socket.EVENT_MESSAGE, Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient client, Object data, AckRequest ackSender) throws Exception {
                System.out.println("接收来自客户端的信息 ： " + data.toString());
                //发送事件
//                server.getBroadcastOperations().sendEvent(Socket.EVENT_MESSAGE, "hi");
                client.sendEvent("ServerEvent", "你好啊，client_" + client.getSessionId());
            }
        });

        //监听带回调事件
        server.addEventListener("ClientEventAck", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, final AckRequest ackSender) throws Exception {
                System.out.println(data);
                if (ackSender.isAckRequested()) {
                    ackSender.sendAckData("ClientEventAck : this msg is server acked!");
                }
            }
        });

        //开启服务器
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }

}
