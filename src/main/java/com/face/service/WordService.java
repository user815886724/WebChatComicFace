package com.face.service;

import com.face.model.WordModel;

import java.util.List;

public interface WordService {

    String recognize(String pictureId)throws Exception;

    List<WordModel> getWordList(String userId);
}
