package com.face.service;

import com.face.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> findAllUser();

    Boolean isHasUserInfo(String openId);

    String login(String openId);

    User getUserById(String id);

    void saveOrUpdateUser(User user);

    String checkUserIdByLogId(String logId)throws Exception;
}
