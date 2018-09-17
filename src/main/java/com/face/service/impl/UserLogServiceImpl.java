package com.face.service.impl;

import com.face.dao.UserLogDao;
import com.face.entity.UserLog;
import com.face.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserLogServiceImpl implements UserLogService{

    @Autowired
    private UserLogDao userLogDao;

    @Override
    public String save(String userId, String sessionId) {
        UserLog userLog = userLogDao.getUserLogByUserIdAndSessionId(userId,sessionId);
        if(userLog != null){
            userLog.setId(userLog.getId());
        }else{
            userLog = new UserLog();
            userLog.setId(UUID.randomUUID().toString());
        }
        userLog.setUserId(userId);
        userLog.setSessionId(sessionId);
        userLog.setLoginTime(new Date());
        return userLogDao.saveAndFlush(userLog).getId();
    }

    @Override
    public String getUserIdById(String id) {
        return userLogDao.findById(id).get().getUserId();
    }
}
