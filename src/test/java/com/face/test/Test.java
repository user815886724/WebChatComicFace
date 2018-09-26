package com.face.test;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Test {
    //设置APPID/AK/SK
    public static final String APP_ID = "14223464";
    public static final String API_KEY = "qYsu9owNq8HGEGrKBbg5mNSA";
    public static final String SECRET_KEY = "ACFiKcgZGCTgtfS2kZcjB1InNjka7q8X";

    public static void main(String[] args) throws Exception{
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        String path = "C:/Users/PabloWayne/Desktop/QQ图片20180918171609.png";
        //普通版例子
        /*JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));*/

        //高精度例子
        sample(client);
    }


    public static void sample(AipOcr client) throws Exception{
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "false");
        options.put("probability", "false");


        // 参数为本地图片路径
        String image = "C:/Users/PabloWayne/Desktop/QQ图片20180918171609.png";
//        JSONObject res = client.basicAccurateGeneral(image, options);
//        System.out.println(res.toString(2));

        // 参数为本地图片二进制数组
        byte[] file = readImageFile(image);
        JSONObject res = client.basicAccurateGeneral(file, options);
        System.out.println(res.toString(2));

    }

    private static byte[] readImageFile(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

}
