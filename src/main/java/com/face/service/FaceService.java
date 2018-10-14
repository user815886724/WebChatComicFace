package com.face.service;

import java.util.Map;

public interface FaceService {
    Map recognize(String pictureId)throws Exception;

    void addData(String pictureId,String shapeId)throws Exception;
}
