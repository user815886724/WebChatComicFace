package com.face.service.impl;

import com.face.dao.UserDao;
import com.face.entity.User;
import com.face.service.UserLogService;
import com.face.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLogService userLogService;

    @Override
    public List<User> findAllUser() {
        return userDao.findAll();
    }

    @Override
    public Boolean isHasUserInfo(String openId) {
        User user = userDao.findUserByOpenId(openId);
        if(user == null ){
            return false;
        }
        return true;
    }

    @Override
    public String login(String openId) {
        User user = userDao.findUserByOpenId(openId);
        if(user != null){
            return user.getId();
        }
        user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setOpenId(openId);
        return userDao.save(user).getId();
    }

    @Override
    public User getUserById(String id) {
        return userDao.findById(id).get();
    }

    @Override
    public void saveOrUpdateUser(User user) {
        userDao.saveAndFlush(user);
    }

    @Override
    public String checkUserIdByLogId(String logId) throws Exception {
        if(StringUtils.isEmpty(logId)){
            throw new Exception("用户未登录！请登录！");
        }
        String userId = userLogService.getUserIdById(logId);
        if(StringUtils.isEmpty(logId)){
            throw new Exception("用户登录日志信息出现异常!");
        }
        //再检查一遍用户是否存在
        User user = this.getUserById(userId);
        if(user == null){
            throw new Exception("用户信息出现异常！");
        }
        return userId;
    }
}
