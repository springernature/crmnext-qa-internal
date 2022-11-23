package com.sn.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.hybridFramework.utility.ResourceHelper;


@SuppressWarnings("rawtypes")
public class LoggerHelper {

	private static boolean root = false;

	public static Logger getLogger(Class clas){
		if (root) {
			return LogManager.getLogger(clas);
		}
		String log4jLocation = System.getProperty("user.dir")+"/src/main/resources/log4j.properties";
		//PropertyConfigurator
		//PropertyConfigurator.configure(ResourceHelper.getResourcePath("/src/main/resources/log4j.properties"));
		root = true;
		return LogManager.getLogger(clas);
	}
}
