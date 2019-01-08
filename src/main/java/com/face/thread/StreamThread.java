package com.face.thread;

import com.face.commom.ConfigCommon;
import com.face.dao.KerasLogDao;
import com.face.entity.KerasLog;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.UUID;

/**
 * @author huangwh
 * @date 2019/1/8
 * @time 13:58
 */
public class StreamThread extends Thread{

    @Autowired
    private KerasLogDao kerasLogDao;

    private static Logger logger = LoggerFactory.getLogger(StreamThread.class);

    private BufferedReader reader;

    private String taskName;

    private String type;

    private String logType;


    public StreamThread(InputStream inputStream){
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public StreamThread(String logType,InputStream inputStream,String taskName,String type){
        this.logType = logType;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.taskName = taskName;
        this.type = type;
    }


    @Override
    public void run() {
        String result = "";
        String line;
        try{
            while ((line = reader.readLine()) != null){
                result += line;
                logger.info(line);
            }
            reader.close();
            if(StringUtils.isNotBlank(taskName) && StringUtils.isNotBlank(type)){
                if(ConfigCommon.LOG_KERAS_TYPE.equals(logType)){
                    KerasLog kerasLog = new KerasLog();
                    kerasLog.setId(UUID.randomUUID().toString());
                    kerasLog.setCreateTime(new Date());
                    kerasLog.setLogDetail(result);
                    kerasLog.setTaskName(taskName);
                    kerasLog.setTypeCode(type);
                    kerasLogDao.save(kerasLog);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
