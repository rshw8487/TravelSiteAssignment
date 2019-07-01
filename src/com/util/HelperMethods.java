package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class HelperMethods {
	String result = "";
	InputStream inputStream;


	public String getPropValue(String key) {
		try {
			String projectDir = System.getProperty("user.dir");
			String propFilePath = projectDir + File.separator + "testdata"
			+ File.separator + "conf.properties";
			
			File file = new File(propFilePath);
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();			
			String value = properties.getProperty(key);
			return value;			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
