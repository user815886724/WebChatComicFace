package com.face.service;

import com.face.entity.Picture;

public interface PictureService {

    void savePicture(String userId,String path,String absolutePath,String fileName);

    Picture getPictureById(String pictureId);
}
