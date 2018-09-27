package com.face.service;

import com.face.entity.Picture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {

    String savePicture(String userId,String path,String absolutePath,String fileName);

    Picture getPictureById(String pictureId);

    String upload(String userId,MultipartFile[] files)throws Exception;

    ResponseEntity<byte[]> download(String pictureId)throws IOException;
}
