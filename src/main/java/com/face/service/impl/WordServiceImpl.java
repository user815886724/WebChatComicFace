package com.face.service.impl;

import com.baidu.aip.ocr.AipOcr;
import com.face.commom.PropertiesUtil;
import com.face.dao.PictureDao;
import com.face.dao.WordDao;
import com.face.entity.Picture;
import com.face.entity.Word;
import com.face.service.WordService;
import com.face.utils.FileUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class WordServiceImpl implements WordService{

    /**
     * 百度文字识别的配置字段
     */
    private static String WORDS_RESULT = "words_result";

    private static String WORDS = "words";

    private static String DETECT_DIRECTION = "detect_direction";

    private static String PROBABILITY = "probability";


    /**
     * 百度的服务器配置
     */
    private static String APP_ID = "Word_APP_ID";

    private static String APP_KEY = "Word_APP_KEY";

    private static String SECRET_KEY = "Word_SECRET_KEY";


    @Autowired
    private WordDao wordDao;

    @Autowired
    private PictureDao pictureDao;

    @Override
    public String recognize(String pictureId)throws Exception{
        //去查路径
        Picture picture = pictureDao.getOne(pictureId);
        String path = picture.getAbsolutePath();
        String userId = picture.getUserId();
        AipOcr client = new AipOcr(PropertiesUtil.getProperties(APP_ID), PropertiesUtil.getProperties(APP_KEY), PropertiesUtil.getProperties(SECRET_KEY));

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        String result = sample(client,path);

        //保存Word信息
        Word word = new Word(UUID.randomUUID().toString(),userId,pictureId,result,new Date(),new Date());
        wordDao.save(word);

        return result;
    }

    private String sample(AipOcr client,String path) throws Exception{
        String result = "";
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put(DETECT_DIRECTION, "false");
        options.put(PROBABILITY, "false");
        byte[] file = FileUtil.readImageFile(path);
        JSONObject res = client.basicAccurateGeneral(file, options);
        JSONArray jsonArray = res.getJSONArray(WORDS_RESULT);
        for(int i = 0;i < jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result += jsonObject.getString(WORDS) + "\n";
        }
        return result;
    }
}
