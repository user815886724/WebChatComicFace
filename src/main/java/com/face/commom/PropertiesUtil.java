package com.face.commom;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static String  getProperties(String key)throws Exception{
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(PropertiesUtil.class.getResource("/static/common.xml").getPath());
        prop.loadFromXML(fis);
        return prop.getProperty(key);
    }
}