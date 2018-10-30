package com.face.controller;

import com.face.entity.Shares;
import com.face.service.SharesService;
import com.face.service.impl.SharesServiceImpl;
import com.face.utils.SharesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/save")
    @ResponseBody
    public void save(){
        SharesUtil sharesUtil = new SharesUtil("601058","20181026","20181026");
        Shares shares = sharesUtil.getSharesEntity();
        sharesService.save(shares);
    }
}
