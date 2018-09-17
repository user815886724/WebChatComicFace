package com.face.service.impl;

import com.face.dao.UserDao;
import com.face.entity.User;
import com.face.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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
}
