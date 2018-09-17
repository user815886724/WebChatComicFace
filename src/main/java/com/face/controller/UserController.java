package com.face.controller;

import com.face.commom.PropertiesUtil;
import com.face.commom.UrlUtils;
import com.face.entity.User;
import com.face.model.UserInfoModel;
import com.face.service.UserLogService;
import com.face.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLogService userLogService;

    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "Hello World";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll(){
        return userService.findAllUser();
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(String code)throws Exception{
        String params = "appid="+PropertiesUtil.getProperties("Wechat.Appid")+"&secret="+PropertiesUtil.getProperties("Wechat.AppSerect")+"&js_code="+code+"&grant_type=authorization_code";
        String result = UrlUtils.sendPost("https://api.weixin.qq.com/sns/jscode2session",params);
        JSONObject jsonObject = new JSONObject(result);
        String openId = jsonObject.getString("openid");
        String sessionId = jsonObject.getString("session_key");
        String id = userService.login(openId);
        String logId = userLogService.save(id,sessionId);
        return logId;
    }


    @PostMapping(value = {"/getUserInfo"})
    @ResponseBody
    public void getUserInfo(@RequestBody UserInfoModel userInfo){
        if(StringUtils.isNotBlank(userInfo.getLogId())){
            String userId= userLogService.getUserIdById(userInfo.getLogId());
            if(StringUtils.isNotBlank(userId)){
                User user = userService.getUserById(userId);
                BeanUtils.copyProperties(userInfo,user);
                userService.saveOrUpdateUser(user);
            }
        }
    }


    @RequestMapping("/test")
    @ResponseBody
    public Boolean test(String openId) throws Exception{
        return userService.isHasUserInfo(openId);
    }
}
