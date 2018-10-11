package com.face.controller;

import com.face.response.CallbackResult;
import com.face.service.FaceService;
import com.face.service.PictureService;
import com.face.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/face/")
public class FaceController {

    @Autowired
    private FaceService faceService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private UserService userService;

    @PostMapping("/recognize")
    @ResponseBody
    public CallbackResult recognize(String pictureId,String logId){
        CallbackResult callbackResult = new CallbackResult(false);
        try{
            //检查用户
            String userId = userService.checkUserIdByLogId(logId);
        }catch (Exception e){
            e.printStackTrace();
            callbackResult.setMessage(e.getMessage());
            return callbackResult;
        }
        return callbackResult;
    }


    @PostMapping("/recognizeAndUpload")
    @ResponseBody
    public CallbackResult recognizeAndUpload(@RequestParam("files")MultipartFile[] files, String logId){
        CallbackResult callbackResult = new CallbackResult(false);
        try{
            //检查用户
            String userId = userService.checkUserIdByLogId(logId);
            //上传图片
            String pictureId = pictureService.upload(userId,files);

        }catch (Exception e){
            e.printStackTrace();
            callbackResult.setMessage(e.getMessage());
            return callbackResult;
        }
        return callbackResult;
    }
}
