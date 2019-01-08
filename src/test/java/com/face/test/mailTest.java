package com.face.test;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

/**
 * @author huangwh
 * @date 2018/12/14
 * @time 9:54
 */
public class mailTest {

    // 短信应用SDK AppID
    private static int appid = 1400171207; // 1400开头

    // 短信应用SDK AppKey
    private static String appkey = "9a642ff8d7d4e2a8f5f29dc974fa8844";

    // 需要发送短信的手机号码
    private static String[] phoneNumbers = {"15814961857"};

    // 短信模板ID，需要在短信应用中申请
    private static int templateId = 247435; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请

    // 签名
    private static String smsSign = "185398"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

    public static void main(String[] args){
        // 单发短信
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.send(0, "86", phoneNumbers[0],
                    "测试", "", "");
            System.out.print(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }
}
