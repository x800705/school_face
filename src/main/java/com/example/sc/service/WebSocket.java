package com.example.sc.service;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket")
@Component
public class WebSocket {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private int a;
    private int b;
    private int c;
    private int d;

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocket> webSocketSet=new CopyOnWriteArraySet<>();



    /**
     *  建立连接成功
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketSet.add(this);
        System.out.println("【websocket消息】 有新的连接，总数{}"+webSocketSet.size());
        this.sendMessage(String.valueOf(webSocketSet.size()));


    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(){
        this.session=session;
        webSocketSet.remove(this);
        System.out.println("【websocket消息】 连接断开，总数{}"+webSocketSet.size());
        this.sendMessage(String.valueOf(webSocketSet.size()));

    }

    /**
     * 接收客户端消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println("【websocket消息】 收到客户端发来的消息：{}"+message);
        if(Objects.equals(message, "start")){
            sendMessage("start_test");
        }

    }

    /**
     * 发送消息
     * @param message
     */
    public static void sendMessage(String message){
        System.out.println("【websocket消息】 发送消息：{}"+message);

        for (WebSocket webSocket:webSocketSet){
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
