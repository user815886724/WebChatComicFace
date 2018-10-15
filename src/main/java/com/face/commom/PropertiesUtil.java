package com.face.commom;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static String  getProperties(String key)throws Exception{
        Properties prop = new Properties();
        InputStream fis = PropertiesUtil.class.getResourceAsStream("/static/common.xml");
        prop.loadFromXML(fis);
        return prop.getProperty(key);
    }
}