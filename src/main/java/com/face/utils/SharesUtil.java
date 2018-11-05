package com.face.utils;

import com.face.entity.Share;
import com.face.entity.ShareStat;
import com.face.entity.Shares;
import com.face.commom.UrlUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author huangwh
 * @date 2018/10/27
 * @time 13:11
 */
public class SharesUtil {



    private static String charset = "GB2312";

    private static String regex = "historySearchHandler";

    private String code;

    private String start;

    private String end;

    public SharesUtil() {
    }

    public SharesUtil(String code, String start, String end) {
        this.code = code;
        this.start = start;
        this.end = end;
    }

    public Shares getSharesEntity(){
        String url = "http://q.stock.sohu.com/hisHq?code=@code&start=@start&end=@end&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp";

        Shares shares = new Shares();
        shares.setId(UUID.randomUUID().toString());
        url = url.replace("@code","cn_"+code);
        url = url.replace("@start",start);
        url = url.replace("@end",end);
        //获取数据
        String result = UrlUtils.sendGet(url,charset);
        //过滤多余数据，提取JSON对象
        result = result.replaceAll(regex,"");
        result = result.substring(1,result.length()-1);
        //开始提取转换
        JSONArray historySearchHandlerArray = JSONArray.fromObject(result);
        if(historySearchHandlerArray.size() > 0){
            JSONObject historySearchHandler = (JSONObject) historySearchHandlerArray.get(0);
            shares.setStatus(historySearchHandler.getString("status"));
            JSONArray hqArray = historySearchHandler.getJSONArray("hq");
            List<Share> shareEntities = new ArrayList<>();
            for(int i = 0;i < hqArray.size();i++){
                JSONArray sharesArray = hqArray.getJSONArray(i);
                Share share = new Share(sharesArray.getString(0),
                        sharesArray.getDouble(1),sharesArray.getDouble(2),sharesArray.getDouble(3),
                        sharesArray.getString(4),sharesArray.getDouble(5),sharesArray.getDouble(6),
                        sharesArray.getDouble(7), sharesArray.getDouble(8),sharesArray.getString(9),code);
                shareEntities.add(share);
            }
            shares.setHq(shareEntities);
            shares.setCode(historySearchHandler.getString("code"));
            JSONArray statArray = historySearchHandler.getJSONArray("stat");
            ShareStat shareStat = new ShareStat(statArray.getString(0),statArray.getString(1),
                    statArray.getDouble(2),statArray.getString(3),statArray.getDouble(4),
                    statArray.getDouble(5),statArray.getDouble(6),statArray.getDouble(7),
                    statArray.getString(8));
            shares.setStat(shareStat);
            shares.setCodeName(getCodeName(code));
        }
        return shares;
    }

    private String getCodeName(String code){
        String getUserNameUrl = "http://hq.sinajs.cn/list=sh@code";
        getUserNameUrl = getUserNameUrl.replace("@code",code);
        String result = UrlUtils.sendGet(getUserNameUrl,charset);
        String codeName = result.split(",")[0];
        codeName = codeName.replace("var hq_str_sh"+code+"=\"" ,"");
        return codeName;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
