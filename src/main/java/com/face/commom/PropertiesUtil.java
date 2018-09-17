package com.face.commom;

import org.springframework.context.ApplicationContext;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static String  getProperties(String key)throws Exception{
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(PropertiesUtil.class.getResource("/static/commom.xml").getPath());
        prop.loadFromXML(fis);
        return prop.getProperty(key);
    }
}
