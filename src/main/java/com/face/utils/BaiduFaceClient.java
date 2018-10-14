package com.face.utils;

import com.baidu.aip.face.AipFace;
import com.face.commom.PropertiesUtil;
import com.face.math.utils.ImageUtil;
import org.json.JSONObject;

import java.util.HashMap;

public class BaiduFaceClient {
    /**
     * 百度人脸识别的服务器配置
     */
    private static String APP_ID = "Face_APP_ID";

    private static String API_KEY = "Face_API_KEY";

    private static String SECRET_KEY = "Face_SECRET_KEY";

    public static JSONObject execute(String path) throws Exception{

        //连接人脸识别客户端
        AipFace client = new AipFace(PropertiesUtil.getProperties(APP_ID), PropertiesUtil.getProperties(API_KEY), PropertiesUtil.getProperties(SECRET_KEY));

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(6000);

        //设置传入参数调用接口
        //询问接口参数情况
        HashMap<String, String> options = new HashMap<>();



        options.put("face_field", "landmark,age");
        // 最高可以检测的人脸
        options.put("max_face_num", "2");
		/*
		 * 类型：LIVE生活照；IDCARD身份证芯片照；WATERMARK水印证件照；CERT证件照片
		 * 默认为LIVE
		 */
        options.put("face_type", "LIVE");
        /*
		 *返回结果说明：
		 *  age年龄;
		 *  beauty美丽程度;
		 *  expression表情;
		 *  faceshape脸型;
		 *  gender性别;
		 *  glasses眼镜;
		 *  landmark：标记点landmark72：标记72个特征点;
		 *  race人种;
		 *  facetype物种;
		 *  quality照片人脸质量，多个用逗号隔开
		 */
        return client.detect(ImageUtil.getBase64Str(path), "BASE64", options);
    }
}
