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

        //得到用户的个人信息
        if(StringUtils.isEmpty(logId)){
            result.setMessage("用户未登录！请登录！");
            return result;
        }
        String userId = userLogService.getUserIdById(logId);
        if(StringUtils.isEmpty(logId)){
            result.setMessage("用户登录日志信息出现异常！");
            return result;
        }
        User user = userService.getUserById(userId);
        if(user == null){
            result.setMessage("用户信息出现异常！");
            return result;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");


        log.info("开始上传");
        //多文件上传
        if(files != null && files.length >= 1){
            try{
                String fileName = files[0].getOriginalFilename();
                //判断是否有文件(实际生产中要判断是否是音频文件)
                if(StringUtils.isNoneBlank(fileName)) {
                    //创建输出文件夹对象
                    String filePath = user.getId()+File.separator + simpleDateFormat.format(new Date());
                    String fileObject = UUID.randomUUID().toString() +"."+ FileUtil.getFileType(fileName);
                    File outFile = new File(PropertiesUtil.getProperties("ImagePath") + filePath + File.separator + fileObject);
                    //拷贝文件到输出文件对象
                    FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
                    pictureService.savePicture(userId,filePath + File.separator + fileObject,PropertiesUtil.getProperties("ImagePath") + filePath + File.separator + fileObject,fileName);
                }
            }catch (Exception e){
                e.printStackTrace();
                result.setMessage(e.getMessage());
                return result;
            }finally {
                result.setSuccess(true);
                result.setMessage("上传成功！");
            }
        }
        return result;
    }


    @RequestMapping("/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(@RequestParam("pictureId") String pictureId) throws IOException{
        Picture picture = pictureService.getPictureById(pictureId);
        File file = new File(picture.getAbsolutePath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",picture.getFileName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

}
