package com.face.test;

import com.baidu.aip.ocr.AipOcr;
import com.face.utils.FileUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class Test {
    //设置APPID/AK/SK
    public static final String APP_ID = "14223464";
    public static final String API_KEY = "qYsu9owNq8HGEGrKBbg5mNSA";
    public static final String SECRET_KEY = "ACFiKcgZGCTgtfS2kZcjB1InNjka7q8X";

    public static void main(String[] args) throws JSONException{
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        String path = "C:/Users/PabloWayne/Desktop/QQ图片20180918171609.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }
}
