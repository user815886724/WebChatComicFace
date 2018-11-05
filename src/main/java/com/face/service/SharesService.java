package com.face.service;

import com.face.entity.PageInfo;
import com.face.entity.Share;
import com.face.entity.Shares;
import com.face.model.SharesModel;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Map;

/**
 * @author huangwh
 * @date 2018/10/28
 * @time 23:08
 */
public interface SharesService {

    void save(Shares shares);

    Map<String,String> getStartAndEndTime(String code,String startTime,String endTime)throws ParseException;

    void clearSurplusTime(String code,String start,String end)throws ParseException;

    PageInfo<SharesModel> getSharesList(Pageable pageable,String code,String codeName);

    long getSharesCount(String code,String codeName);

    PageInfo<Share> getShareList(Pageable pageable,String id);

    long getShareCount(String id);
}
