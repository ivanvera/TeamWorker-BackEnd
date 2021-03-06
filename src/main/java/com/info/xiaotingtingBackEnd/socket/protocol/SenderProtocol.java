package com.info.xiaotingtingBackEnd.socket.protocol;

/**
 * Copyright (c) 2017, Chestnut All rights reserved
 * Author: Chestnut
 * CreateTime：at 2017/12/31 09:43:53
 * Description：发送客户端消息协议
 * Email: xiaoting233zhang@126.com
 */
public class SenderProtocol {

    /**
     * 账号在其他设备登录，强制下线
     */
    public static final int DUPLICATE_LOGIN = 1004;

    /**
     * 发送客户端一条新消息
     */
    public static final int MSG_SEND_NORMAL_MESSAGE = 1005;

    /**
     * 发送客户端一条新的好友请求
     */
    public static final int MSG_SEND_FRIEND_REQUEST = 1006;


    /**
     * 发送客户端一条聊天室消息
     * 发送对象 ChatMessage
     */
    public static final int MSG_SEND_CHAT_MESSAGE = 2000;

    /**
     * 发送客户端多条聊天室消息
     * 发送对象 List<ChatMessage>
     */
    public static final int MSG_SEND_CHAT_MANY_MESSAGE = 2001;
    /**
     * 发送客户端消息成功
     * 发送对象 id
     */
    public static final int MSG_SEND_CHAT_MESSAGE_DONE = 2002;

    /**
     * 发送通知
     * 发送对象 Notification
     */
    public static final int MSG_SEND_NOTIFICATION = 3000;

    /**
     * 发送多条通知
     * 发送对象 List<Notification>
     */
    public static final int MSG_SEND_MULTI_NOTIFICATION = 3001;
}
