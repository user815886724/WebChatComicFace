package com.face.service.impl;

import com.face.commom.PropertiesUtil;
import com.face.dao.PictureDao;
import com.face.entity.Picture;
import com.face.service.PictureService;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String savePicture(String userId,String path,String absolutePath,String fileName) {
        String id = UUID.randomUUID().toString();
        Date createTime = new Date();
        Date updateTime = new Date();
        Picture picture = new Picture(id,userId,path,absolutePath,fileName,createTime,updateTime);
        return pictureDao.save(picture).getId();
    }

    @Override
    public Picture getPictureById(String pictureId) {
        return pictureDao.getOne(pictureId);
    }

    @Override
    public String upload(String userId, MultipartFile[] files) throws Exception {
        String pictureId = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
        logger.info("开始上传");
        //文件上传
        if(files != null && files.length >= 1){
            String fileName = files[0].getOriginalFilename();
            //判断是否有文件(实际生产中要判断是否是音频文件)
            if(StringUtils.isNoneBlank(fileName)) {
                //创建输出文件夹对象
                String filePath = userId+ File.separator + simpleDateFormat.format(new Date());
                String fileObject = UUID.randomUUID().toString() +"."+ FileUtil.getFileType(fileName);
                File outFile = new File(PropertiesUtil.getProperties("ImagePath") + filePath + File.separator + fileObject);
                //拷贝文件到输出文件对象
                FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
                pictureId = this.savePicture(userId,filePath + File.separator + fileObject,PropertiesUtil.getProperties("ImagePath") + filePath + File.separator + fileObject,fileName);
            }
        }
        return pictureId;
    }

    @Override
    public ResponseEntity<byte[]> download(String pictureId) throws IOException{
        Picture picture = this.getPictureById(pictureId);
        File file = new File(picture.getAbsolutePath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",picture.getFileName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}
