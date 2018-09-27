package com.face.controller;

import com.face.commom.PropertiesUtil;
import com.face.entity.Picture;
import com.face.entity.User;
import com.face.response.CallbackResult;
import com.face.service.PictureService;
import com.face.service.UserLogService;
import com.face.service.UserService;
import com.face.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping(value = "/pic/")
public class PicController {

    private final static Logger log = LoggerFactory.getLogger(PicController.class);


    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    @PostMapping("/upload")
    @ResponseBody
    public CallbackResult upload(@RequestParam("files")MultipartFile[] files,String logId){
        CallbackResult result = new CallbackResult(false);
        String pictureId = "";
        try{
            String userId = userService.checkUserIdByLogId(logId);
            pictureId = pictureService.upload(userId,files);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            return result;
        }
        Map param = new HashMap();
        param.put("pictureId",pictureId);
        result.setSuccess(true);
        result.setDetails(param);
        result.setMessage("上传成功！");
        return result;
    }


    @RequestMapping("/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(@RequestParam("pictureId") String pictureId) throws IOException{
        return pictureService.download(pictureId);
    }
}
