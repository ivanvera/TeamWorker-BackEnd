package com.info.xiaotingtingBackEnd.repository;

import com.info.xiaotingtingBackEnd.model.Chat;
import com.info.xiaotingtingBackEnd.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright (c) 2018, Chestnut All rights reserved
 * Author: Chestnut
 * CreateTime：at 2018/4/10 15:19:10
 * Description：
 * Email: xiaoting233zhang@126.com
 */
@Repository
public interface ChatRep extends BaseRepository<Chat, String> {

    void deleteByUserListAndChatType(String userList,int chatType);

    Chat findTopByUserListAndChatType(String userList,int chatType);
}
