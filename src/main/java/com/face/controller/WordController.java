package com.face.controller;

import com.face.response.CallbackResult;
import com.face.service.PictureService;
import com.face.service.UserService;
import com.face.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/word/")
public class WordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private WordService wordService;

    /**
     *
     * @param files 上传的图片集
     * @param logId 登录的日志Id
     * @return 图片识别的结果
     */
    public CallbackResult result(@RequestParam("files")MultipartFile[] files, String logId){
        CallbackResult result = new CallbackResult(false);
        String recognizeResult = "";
        try{
            //检查用户
            String userId = userService.checkUserIdByLogId(logId);
            //上传图片
            String pictureId = pictureService.upload(userId,files);
            //识别图片
            recognizeResult = wordService.recognize(pictureId);
            result.setSuccess(true);
            result.setDetails(recognizeResult);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }
}
