package com.face.service.impl;

import com.face.dao.ShareDao;
import com.face.dao.SharesDao;
import com.face.entity.PageInfo;
import com.face.entity.Share;
import com.face.entity.Shares;
import com.face.model.SharesModel;
import com.face.service.SharesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.GeneratedValue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author huangwh
 * @date 2018/10/28
 * @time 23:08
 */
@Service
public class SharesServiceImpl implements SharesService {

    @Autowired
    private SharesDao sharesDao;

    @Autowired
    private ShareDao shareDao;

    @Override
    public void save(Shares shares) {
        sharesDao.save(shares);
    }



    //清除多余的查询出的时间
    @Override
    public void clearSurplusTime(String code, String start, String end) throws ParseException {
        List<String> dateList = this.getDateList(start,end);
        for(String date : dateList){
            Share share = shareDao.findByShareCodeAndTime(code,date);
            if(share != null){
                Shares shares = sharesDao.findById(share.getSharesId()).get();
                sharesDao.delete(shares);
            }
        }
    }



    @Override
    public Map<String,String> getStartAndEndTime(String code,String startTime,String endTime) throws ParseException{
        Map<String,String> map = new HashMap<>();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Shares shares = sharesDao.findByCode("cn_"+code);
        if(shares == null){
            map.put("startTime",dateFormat.format(inputFormat.parse(startTime)));
            map.put("endTime",dateFormat.format(inputFormat.parse(endTime)));
            return map;
        }
        for(int i = 0; i < shares.getHq().size(); i++){
            if(i == 0){
                map.put("startTime",dateFormat.format(inputFormat.parse(shares.getHq().get(i).getTime())));
                map.put("endTime",dateFormat.format(inputFormat.parse(shares.getHq().get(i).getTime())));
            }else{
                if(dateFormat.parse(map.get("startTime")).after(inputFormat.parse(shares.getHq().get(i).getTime()))){
                    map.put("startTime",dateFormat.format(inputFormat.parse(shares.getHq().get(i).getTime())));
                }
                if(dateFormat.parse(map.get("endTime")).before(inputFormat.parse(shares.getHq().get(i).getTime()))){
                    map.put("endTime",dateFormat.format(inputFormat.parse(shares.getHq().get(i).getTime())));
                }
            }
        }

        Date startDate = inputFormat.parse(startTime);
        Date endDate = inputFormat.parse(endTime);

        if(startDate.before(dateFormat.parse(map.get("startTime")))){
            map.put("startTime",dateFormat.format(startDate));
        }
        if(endDate.after(dateFormat.parse(map.get("endTime")))){
            map.put("endTime",dateFormat.format(endDate));
        }
        return map;
    }

    @Override
    public PageInfo<SharesModel> getSharesList(Pageable pageable,String code,String codeName) {
        //设置查询对象
        Shares sharesQuery = new Shares();
        sharesQuery.setCode(code);
        sharesQuery.setCodeName(codeName);
        //创建匹配器，即如何使用查询条件,模糊匹配
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("codeName", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("focus"); //忽略属性：是否关注。因为是基本类型，需要忽略掉
        Example<Shares> example = Example.of(sharesQuery, matcher);

        Page<Shares> page = sharesDao.findAll(example,pageable);
        PageInfo<SharesModel> pageInfo = new PageInfo<>(page);
        List<SharesModel> sharesModelList = new ArrayList<>();
        for(Shares shares : page){
            SharesModel model = new SharesModel();
            model.setId(shares.getId());
            model.setCode(shares.getCode());
            model.setCodeName(shares.getCodeName());
            model.setTimeDesc(shares.getStat().getTimeDesc());
            sharesModelList.add(model);
        }
        pageInfo.setDatas(sharesModelList);
        return pageInfo;
    }

    @Override
    public long getSharesCount(String code,String codeName) {
        return sharesDao.getCountByCodeOrCodeName(code,codeName);
    }


    @Override
    public PageInfo<Share> getShareList(Pageable pageable, String id) {
        Share share = new Share();
        share.setSharesId(id);
        Example<Share> example = Example.of(share);
        Page<Share> page = shareDao.findAll(example,pageable);
        PageInfo<Share> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }

    @Override
    public long getShareCount(String id) {
        Share share = new Share();
        share.setSharesId(id);
        Example<Share> example = Example.of(share);
        return shareDao.count(example);
    }

    //分割提取已有的日期
    private List<String> extractDate(List<String> times,String start,String end)throws ParseException{
        List<String> resultTime = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse(start);
        Date endDate = format.parse(end);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        endCalendar.add(Calendar.DAY_OF_YEAR,1);
        while (startCalendar.before(endCalendar)){
            if(! times.contains(timeFormat.format(startCalendar.getTime()))){
                resultTime.add(format.format(startCalendar.getTime()));
            }
            startCalendar.add(Calendar.DAY_OF_YEAR,1);
        }

        return resultTime;
    }



    //获取日期列表
    private List<String> getDateList(String start, String end) throws ParseException{
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse(start);
        Date endDate = format.parse(end);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.add(Calendar.DAY_OF_YEAR,1);
        while (startCalendar.before(endCalendar)){
            dateList.add(timeFormat.format(startCalendar.getTime()));
            startCalendar.add(Calendar.DAY_OF_YEAR,1);
        }
        return dateList;
    }


}
