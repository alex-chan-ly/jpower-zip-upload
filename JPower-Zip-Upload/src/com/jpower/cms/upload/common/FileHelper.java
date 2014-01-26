package com.jpower.cms.upload.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileHelper {
        private static String propertiesFileName = "config.properties";
        private static Properties prop = null;
       
        public static void loadProperties() {
                try {
                		prop = new Properties();
                        InputStream is = FileHelper.class.getClassLoader().getResourceAsStream(propertiesFileName);
                        prop.load(is);
                       
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
               
        public static String getConfigProperty(String name) {
        		if (prop == null) {
        			loadProperties();
        		}
        		
                if (name != null && name.length() > 0) {
                        return prop.getProperty(name);
                } else {
                        return null;
                }
        }
        
 }
