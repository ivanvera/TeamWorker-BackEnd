package com.info.xiaotingtingBackEnd.service;

import com.info.xiaotingtingBackEnd.constants.HttpResponseCodes;
import com.info.xiaotingtingBackEnd.model.User;
import com.info.xiaotingtingBackEnd.model.UserRelation;
import com.info.xiaotingtingBackEnd.model.vo.UserVo;
import com.info.xiaotingtingBackEnd.pojo.ApiResponse;
import com.info.xiaotingtingBackEnd.pojo.DepartmentUser;
import com.info.xiaotingtingBackEnd.repository.UserRep;
import com.info.xiaotingtingBackEnd.repository.base.SearchBean;
import com.info.xiaotingtingBackEnd.repository.base.SearchCondition;
import com.info.xiaotingtingBackEnd.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2018, Chestnut All rights reserved
 * Author: Chestnut
 * CreateTime：at 2018/4/1 14:23:58
 * Description：
 * Email: xiaoting233zhang@126.com
 */
@Service
public class UserService extends BaseService<User, String, UserRep> {

    public boolean idAuth(String uid, String token) {
        User user = userRep.findOne(uid);
        if (null == user) {
            return false;
        }
        if (user.getToken().equals(token)) {
            return true;
        }
        return false;
    }

    public User findByAccount(String account) {
        return userRep.findByAccount(account);
    }

    public User findByAccountAndPassword(String account, String password) {
        return userRep.findByAccountAndPassword(account, password);
    }

    public boolean addUserRelation(String userAId, String userBId) {
        UserRelation userRelation = new UserRelation();
        userRelation.setUserBId(userBId);
        userRelation.setUserAId(userAId);
        if (userRelationRep.save(userRelation) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteUserRelation(String userAId, String userBId) {
        UserRelation userRelation = new UserRelation(userAId,userBId);
        userRelationRep.delete(userRelation);
        userRelation.setUserAId(userBId);
        userRelation.setUserBId(userAId);
        userRelationRep.delete(userRelation);
    }

    public ApiResponse<List<DepartmentUser>> getUserByDepartment(int pageNum, int pageSize, ApiResponse<List<DepartmentUser>> apiResponse, String departmentId) {
        Pageable pageable = new PageRequest(pageNum - 1, pageSize);
        Page<DepartmentUser> userList = userRep.getUserByDepartment(departmentId, pageable);
        apiResponse.setCurrentPage(pageNum);
        apiResponse.setPageSize(pageSize);
        apiResponse.setMaxCount((int) userList.getTotalElements());
        apiResponse.setMaxPage(userList.getTotalPages());
        apiResponse.setStatus(HttpResponseCodes.SUCCESS);
        apiResponse.setMessage("获取部门成员成功");
        apiResponse.setData(userList.getContent());
        return apiResponse;
    }

    public boolean isMyFriend(String myUserId, String userId) {
        UserRelation.UserRelationId userRelation = new UserRelation.UserRelationId(myUserId, userId);
        if (userRelationRep.findOne(userRelation) != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<UserVo> getMyFriends(String userId) {
        return userRep.getMyFriend(userId);
    }

    @Override
    public UserRep getRepo() {
        return userRep;
    }
}
