package com.face.service.impl;

import com.face.dao.ConfigDao;
import com.face.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangwh
 * @date 2019/1/7
 * @time 17:46
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigDao dao;

    @Override
    public String getConfigValue(String code) {
        return dao.findValueByCode(code);
    }
}
