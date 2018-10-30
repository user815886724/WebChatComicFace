package com.face.shares;

import com.face.commom.UrlUtils;
import com.face.entity.Shares;
import com.face.service.SharesService;
import com.face.service.impl.SharesServiceImpl;
import com.face.utils.SharesUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author huangwh
 * @date 2018/10/26
 * @time 16:20
 */
public class sharesUtil {


    private static String url = "http://q.stock.sohu.com/hisHq?code=@code&start=@start&end=@end&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp";

    public static void main(String[] args){
        url = url.replace("@code","cn_601058");
        url = url.replace("@start","20181026");
        url = url.replace("@end","20181026");
        String result = UrlUtils.sendGet(url,"GB2312");
        System.out.println(result);
        String regex = "historySearchHandler";
        result = result.replaceAll(regex,"");
        result = result.substring(1,result.length()-1);
        JSONArray jsonObject = JSONArray.fromObject(result);
        System.out.println(jsonObject.get(0));

        SharesUtil sharesUtil = new SharesUtil("601058","20181026","20181026");
        Shares shares = sharesUtil.getSharesEntity();
        SharesServiceImpl sharesService = new SharesServiceImpl();
        sharesService.save(shares);
        System.out.println(shares);
    }
}
