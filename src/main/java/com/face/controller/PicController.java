package com.face.controller;

import com.face.commom.PropertiesUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @RequestMapping("upload")
    @ResponseBody
    public CallbackResult upload(@RequestParam("files")MultipartFile[] files,String logId){
        CallbackResult callbackResult = new CallbackResult(false);

        //得到用户的个人信息
        if(StringUtils.isEmpty(logId)){
            callbackResult.setMessage("用户未登录！请登录！");
            return callbackResult;
        }
        String userId = userLogService.getUserIdById(logId);
        if(StringUtils.isEmpty(logId)){
            callbackResult.setMessage("用户登录日志信息出现异常！");
            return callbackResult;
        }
        User user = userService.getUserById(userId);
        if(user == null){
            callbackResult.setMessage("用户信息出现异常！");
            return callbackResult;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        log.info("开始上传");
        //多文件上传
        if(files != null && files.length >= 1){
            try{
                String fileName = files[0].getOriginalFilename();
                //判断是否有文件(实际生产中要判断是否是音频文件)
                if(StringUtils.isNoneBlank(fileName)) {
                    //创建输出文件对象
                    String filePath = user.getId()+File.separator + simpleDateFormat.format(new Date()) +
                            File.separator + UUID.randomUUID().toString() + FileUtil.getFileType(fileName);
                    File outFile = new File(PropertiesUtil.getProperties("ImagePath") + filePath);
                    //拷贝文件到输出文件对象
                    FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
                    pictureService.savePicture(userId,filePath);
                }
            }catch (Exception e){
                e.printStackTrace();
                callbackResult.setMessage(e.getMessage());
            }finally {
                callbackResult.setSuccess(true);
                callbackResult.setMessage("上传成功！");
            }
        }
        return callbackResult;
    }
}
