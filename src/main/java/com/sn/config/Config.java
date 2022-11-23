package com.sn.config;

import java.io.IOException;
import java.util.Properties;

/***
 * 
 * @author mgp0966
 *@info Read data from config file
 */
public class Config extends BaseDriver {

	

	private Properties OR;
	
	public Config() throws IOException{
		this.OR = readPropertiesFile(PROPFILENAME);
	}
	public String getUserName() {
		return OR.getProperty("Username");
	}

	public String getPassword() {
		return OR.getProperty("Password");
	}

	public String getWebsite() {
		return OR.getProperty("URL");
	}

	public int getPageLoadTimeOut() {
		return Integer.parseInt(OR.getProperty("PageLoadTimeOut"));
	}

	public int getImplicitWait() {
		return Integer.parseInt(OR.getProperty("ImplcitWait"));
	}

	public int getExplicitWait() {
		return Integer.parseInt(OR.getProperty("ExplicitWait"));
	}

	public String getDbType() {
		return OR.getProperty("DataBase.Type");
	}

	public String getDbConnStr() {
		return OR.getProperty("DtaBase.ConnectionStr");
	}
	public String getBrowser() {
		return OR.getProperty("Browser");
	}

}
