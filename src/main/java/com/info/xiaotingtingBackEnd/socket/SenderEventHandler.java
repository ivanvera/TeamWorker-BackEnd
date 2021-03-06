package com.info.xiaotingtingBackEnd.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.info.xiaotingtingBackEnd.model.*;
import com.info.xiaotingtingBackEnd.socket.protocol.SenderProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017, Chestnut All rights reserved
 * Author: Chestnut
 * CreateTime：at 2017/12/11 16:34:41
 * Description：发送客户端消息
 * Email: xiaoting233zhang@126.com
 */
@Component
public class SenderEventHandler extends BaseSocketEventHandler {

    @Autowired
    public SenderEventHandler(SocketIOServer server) {
        super(server);
    }

    /**
     * 系统消息推送
     *
     * @param msgId
     * @param map
     */
    public void sendMessageToUser(int msgId, Map<String, Object> map) {
        String uid = (String) map.get("uid");
        Object message = map.get("message");

        SocketIOClient socketIOClient = clientHashMap.get(uid);
        if (socketIOClient == null) {
            logger.info("Class SenderEventHandler:socketIOClient不存在");
            return;
        }

        socketIOClient.sendEvent(TAG_USER_RECEIVER_MESSAGE, msgId, message);
    }

    /**
     * 用户发送消息
     *
     * @param message
     */
    public boolean sendMessage(Message message) {
        String uid = message.getReceiverId();

        SocketIOClient socketIOClient = clientHashMap.get(uid);
        if (socketIOClient == null) {
            logger.info("sendOderResultToUser socketIOClient不存在");
            return false;
        }

        socketIOClient.sendEvent(TAG_USER_RECEIVER_MESSAGE, SenderProtocol.MSG_SEND_NORMAL_MESSAGE, message);
        return true;
    }

    /**
     * @param newFriendRequest
     * @return
     */
    public boolean sendFriendRequest(NewFriendRequest newFriendRequest) {
        SocketIOClient socketIOClient = clientHashMap.get(newFriendRequest.getRecipientId());
        if (socketIOClient == null) {
            logger.info("sendOderResultToUser socketIOClient不存在");
            return false;
        }
        socketIOClient.sendEvent(TAG_USER_RECEIVER_MESSAGE, SenderProtocol.MSG_SEND_FRIEND_REQUEST, newFriendRequest);
        return true;
    }

    /**
     * 系统下线(踢下线)
     *
     * @param uid
     * @param oldToken
     */
    public void offline(String uid, String oldToken) {
        kickOffClient(uid, oldToken);
    }

    public void sendChatMessage(List<ChatMessage> chatMessageList) {
        for (ChatMessage chatMessage : chatMessageList) {
            SocketIOClient socketIOClient = clientHashMap.get(chatMessage.getUserId());
            if (socketIOClient == null) {
                logger.info("sendOderResultToUser socketIOClient不存在");
                continue;
            }
            socketIOClient.sendEvent(TAG_USER_RECEIVER_MESSAGE, SenderProtocol.MSG_SEND_CHAT_MESSAGE, chatMessage);
        }
    }

    public void sendAllChatMessage(String uid, List<ChatMessage> chatMessageList) {
        if (chatMessageList.isEmpty()) return;
        SocketIOClient socketIOClient = clientHashMap.get(uid);
        if (socketIOClient == null) {
            logger.info("sendOderResultToUser socketIOClient不存在");
            return;
        }
        socketIOClient.sendEvent(TAG_USER_RECEIVER_MESSAGE, SenderProtocol.MSG_SEND_CHAT_MANY_MESSAGE, chatMessageList);
    }

    /**
     * 发送消息完成
     *
     * @param uid
     * @param chatMessageId
     */
    public void sendChatMessageDone(String uid, String chatMessageId) {
        SocketIOClient socketIOClient = clientHashMap.get(uid);
        if (socketIOClient == null) {
            logger.info("sendOderResultToUser socketIOClient不存在");
            return;
        }
        socketIOClient.sendEvent(TAG_USER_RECEIVER_MESSAGE, SenderProtocol.MSG_SEND_CHAT_MESSAGE_DONE, chatMessageId);
    }

    public void sendNotification(List<Notification> notificationList) {
        for (Notification notification : notificationList) {
            SocketIOClient socketIOClient = clientHashMap.get(notification.getReceiverId());
            if (socketIOClient == null) {
                logger.info("sendOderResultToUser socketIOClient不存在");
                continue;
            }
            socketIOClient.sendEvent(TAG_USER_RECEIVER_NOTIFICATION, SenderProtocol.MSG_SEND_NOTIFICATION, notification);
        }
    }

    public void sendAllNotification(String uid, List<Notification> notificationList) {
        if (notificationList.isEmpty()) return;
        SocketIOClient socketIOClient = clientHashMap.get(uid);
        if (socketIOClient == null) {
            logger.info("sendOderResultToUser socketIOClient不存在");
            return;
        }
        socketIOClient.sendEvent(TAG_USER_RECEIVER_NOTIFICATION, SenderProtocol.MSG_SEND_MULTI_NOTIFICATION, notificationList);
    }
}
