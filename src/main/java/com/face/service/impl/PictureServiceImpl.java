package com.face.service.impl;

import com.face.dao.PictureDao;
import com.face.entity.Picture;
import com.face.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public void savePicture(String userId,String path) {
        String id = UUID.randomUUID().toString();
        Date createTime = new Date();
        Date updateTime = new Date();
        Picture picture = new Picture(id,userId,path,null,createTime,updateTime);
        pictureDao.save(picture);
    }
}
