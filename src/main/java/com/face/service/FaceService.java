package com.face.service;

import java.util.Map;

public interface FaceService {
    Map recognize(String pictureId)throws Exception;
}
