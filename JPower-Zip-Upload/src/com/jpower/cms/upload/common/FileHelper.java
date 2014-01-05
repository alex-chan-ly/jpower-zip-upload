package com.jpower.cms.upload.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileHelper {
	private static String propertiesFileName = "config.properties";
	private static Properties prop = new Properties();
	
	public static void loadProperties() {
		try {
			InputStream is = FileHelper.class.getClassLoader().getResourceAsStream(propertiesFileName);
			prop.load(is);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public static String getConfigProperty(String name) {
		if (name != null && !name.isEmpty()) {
			return prop.getProperty(name);
		} else {
			return null;
		}
	}
}
