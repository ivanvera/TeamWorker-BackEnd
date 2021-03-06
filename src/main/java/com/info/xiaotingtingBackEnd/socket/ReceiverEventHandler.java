package com.info.xiaotingtingBackEnd.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.google.gson.reflect.TypeToken;
import com.info.xiaotingtingBackEnd.model.ChatMessage;
import com.info.xiaotingtingBackEnd.model.Notification;
import com.info.xiaotingtingBackEnd.service.ChatService;
import com.info.xiaotingtingBackEnd.service.NotificationService;
import com.info.xiaotingtingBackEnd.socket.protocol.ReceiverProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017, Chestnut All rights reserved
 * Author: Chestnut
 * CreateTime：at 2017/12/11 16:34:41
 * Description：接收客户端消息
 * Email: xiaoting233zhang@126.com
 */
@Component
public class ReceiverEventHandler extends BaseSocketEventHandler {

    @Autowired
    NotificationService notificationService;

    @Autowired
    ChatService chatService;

    @Autowired
    private SenderEventHandler handler;

    @Autowired
    public ReceiverEventHandler(SocketIOServer server) {
        super(server);
    }

    /**
     * 客户端连接事件和接收消息监听器
     *
     * @param client
     * @param msgId
     * @param obj
     */
    @OnEvent(value = TAG_USER_SEND_MESSAGE)
    public void connectAndRceiverServer(SocketIOClient client, String uid, String token, int msgId, String obj) {
        try {
            logger.info("TAG_USER_SEND_MESSAGE 接收到用户app消息 client sessionId: " + client.getSessionId() + " uid: " + uid + " token: " + token + " msgId: " + msgId + " obj: " + obj);
            handleMessage(client, uid, token, msgId, obj);
        } catch (Exception e) {
            logger.info("消息接收失败 " + e.getMessage());
            logger.info("防止恶意连接,下线客户端");
            //服务器主动下线客户端
            client.disconnect();
            e.printStackTrace();
        }
    }

    /**
     * 处理客户端发生过来的消息
     *
     * @param client
     * @param uid
     * @param token
     * @param requestTypeId
     * @param obj
     */
    private void handleMessage(SocketIOClient client, String uid, String token, int requestTypeId, String obj) {
        switch (requestTypeId) {
            case ReceiverProtocol.MSG_CONNECTONION_MESSAGE://连接请求，添加到列表里，并保证只有唯一uid
            {
                client.set("uid", uid);
                client.set("token", token);
                SocketIOClient socketIOClient = clientHashMap.get(uid);
                if (socketIOClient != null) {
                    String oldToken = socketIOClient.get("token");
                    if (oldToken == null || !oldToken.equals(token)) {
                        clientHashMap.remove(uid);
                        clientHashMap.put(uid, client);
                    }//一样就不操作
                } else {
                    clientHashMap.put(uid, client);
                }
                checkNotifyMessage(uid);
                checkNotifyNotification(uid);
                break;
            }
            case ReceiverProtocol.MSG_ISSEND_NOTIFICATION://已接收通知消息
                notificationService.hasSendNotification(obj);
                break;
            case ReceiverProtocol.MSG_ISSEND_MESSAGE://已接收消息
                notificationService.hasSendNotification(obj);
                break;
            case ReceiverProtocol.MSG_ISSEND_CHAT_MESSAGE://已接收聊天室消息
                chatService.hasSendChatMessage(obj);
                break;
            default:
                break;
        }
    }

    /**
     * 检查是否有消息发送给用户
     *
     * @param uid
     */
    private void checkNotifyMessage(String uid) {
        List<ChatMessage> chatMessageList = chatService.getChatMessageList(uid);
        handler.sendAllChatMessage(uid, chatMessageList);
    }

    /**
     * 检查是否有通知发送给用户
     *
     * @param uid
     */
    private void checkNotifyNotification(String uid) {
        List<Notification> notificationList = notificationService.getNotificationList(uid);
        handler.sendAllNotification(uid, notificationList);
    }


    @OnConnect
    public void onConnect(SocketIOClient client) {
        logger.info("connected sessionId: " + client.getSessionId());
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String uid = client.get("uid");
        if (uid != null && !"".equals(uid))
            clientHashMap.remove(uid);
        logger.info("disconnected sessionId: " + client.getSessionId());
    }

}
