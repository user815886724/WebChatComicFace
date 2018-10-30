package com.face.service.impl;

import com.face.dao.SharesDao;
import com.face.entity.Shares;
import com.face.service.SharesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangwh
 * @date 2018/10/28
 * @time 23:08
 */
@Service
public class SharesServiceImpl implements SharesService {

    @Autowired
    private SharesDao sharesDao;

    @Override
    public void save(Shares shares) {
        sharesDao.save(shares);
    }
}
