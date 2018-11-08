package com.face.controller;

import com.face.entity.PageInfo;
import com.face.entity.Share;
import com.face.entity.Shares;
import com.face.model.SharesModel;
import com.face.response.CallbackResult;
import com.face.service.SharesService;
import com.face.utils.SharesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

/**
 * @author huangwh
 * @date 2018/10/28
 * @time 23:56
 */
@RestController
@RequestMapping(value = "shares")
public class SharesController {

    @Autowired
    private SharesService sharesService;

    @PostMapping("/save")
    @ResponseBody
    public CallbackResult save(String code,String startTime,String endTime){
        CallbackResult callbackResult = new CallbackResult(false);
        try{
            Map<String,String> map = sharesService.getStartAndEndTime(code,startTime,endTime);
            sharesService.clearSurplusTime(code,map.get("startTime"),map.get("endTime"));
            SharesUtil sharesUtil = new SharesUtil(code,map.get("startTime"),map.get("endTime"));
            Shares shares = sharesUtil.getSharesEntity();
            sharesService.save(shares);
            callbackResult.setSuccess(true);
            callbackResult.setDetails("插入成功！");
        }catch (ParseException e){
            callbackResult.setMessage("日期输入格式错误");
        }catch (Exception e){
            callbackResult.setMessage(e.toString());
        }
        return callbackResult;
    }

    @GetMapping("/getSharesList")
    @ResponseBody
    public PageInfo<SharesModel> getSharesList(@PageableDefault(value = 10,sort = {"code"},direction = Sort.Direction.DESC)Pageable pageable,@RequestParam(value = "code",required = false)String code,
                                               @RequestParam(value = "codeName",required = false)String codeName){
        return sharesService.getSharesList(pageable,code,codeName);
    }

    @GetMapping("/getSharesCount")
    @ResponseBody
    public long getSharesCount(@RequestParam(value = "code",required = false)String code,
                                         @RequestParam(value = "codeName",required = false)String codeName){
        long count = sharesService.getSharesCount(code,codeName);
        return count;
    }



    @GetMapping("/getShareList")
    @ResponseBody
    public PageInfo<Share> getShareList(@PageableDefault(value = 10,sort = {"time"},direction = Sort.Direction.DESC)Pageable pageable,String id) {
        return sharesService.getShareList(pageable,id);
    }

    @GetMapping("/getShareCount")
    @ResponseBody
    public long getShareCount(String id){
        return sharesService.getShareCount(id);
    }


    @GetMapping("/getChartOption")
    @ResponseBody
    public CallbackResult getChartOption(String id,@RequestParam(name = "startTime",required = false)String startTime,
                                         @RequestParam(name = "endTime",required = false)String endTime){
        CallbackResult callbackResult = new CallbackResult(false);
        callbackResult.setDetails(sharesService.getChartData(id,startTime,endTime));
        callbackResult.setSuccess(true);
        return callbackResult;
    }
}