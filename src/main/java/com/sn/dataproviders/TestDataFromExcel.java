package com.sn.dataproviders;

/**
 * @author mgp0966 Madhav Gutte
 */


import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;




public class TestDataFromExcel {
	
	private static final Logger LOGGER = LogManager.getLogger(TestDataFromExcel.class);
	private String currentDirectory = System.getProperty("user.dir");
	private String filename = currentDirectory + "\\TestData\\TestData.xlsx";
	private static ReadExcel readExcel = new ReadExcel();

	/***
	 * 	@author mgp0966
	 * @return data Object[][]
	 * @throws IOException
	 */
	
	@DataProvider(name = "BPData")
	public Object[][] bpGetTestData() throws IOException
	{
		Object[][] data = null;
		
		try
		{
			//data = readExcel.readExcel(filename, "ApiTestData.xlsx", "BP");
			data = readExcel.getExcelData(filename, "BP");
			
		}catch (final IOException e)
		{
			LOGGER.error(e.getMessage());
			throw e;
		}
			
		return data;
		
	}

	

	/***
	 * 	@author mgp0966
	 * @return data Object[][]
	 * @throws IOException
	 */
	
	@DataProvider(name = "OrganizationData")
	public Object[][] orgBpGetTestData() throws IOException
	{
		Object[][] data = null;
		
		try
		{
			//data = readExcel.readExcel(filename, "ApiTestData.xlsx", "BP");
			data = readExcel.getExcelData(filename, "Organization");
			
		}catch (final IOException e)
		{
			LOGGER.error(e.getMessage());
			throw e;
		}
			
		return data;
		
	}
	
	

	@DataProvider(name = "LeadData")
	public Object[][] leadTestData() throws IOException
	{
		Object[][] data = null;
		
		try
		{
			//data = readExcel.readExcel(filename, "ApiTestData.xlsx", "BP");
			data = readExcel.getExcelData(filename, "Lead");
			
		}catch (final IOException e)
		{
			LOGGER.error(e.getMessage());
			throw e;
		}
			
		return data;
		
	}
	
	@DataProvider(name = "ContactData")
	public Object[][] contactTestData() throws IOException
	{
		Object[][] data = null;
		
		try
		{
			//data = readExcel.readExcel(filename, "ApiTestData.xlsx", "BP");
			data = readExcel.getExcelData(filename, "Contact");
			
		}catch (final IOException e)
		{
			LOGGER.error(e.getMessage());
			throw e;
		}
			
		return data;
		
	}
	
	@DataProvider(name = "AccountData")
	public Object[][] accountTestData() throws IOException
	{
		Object[][] data = null;
		
		try
		{
			//data = readExcel.readExcel(filename, "ApiTestData.xlsx", "BP");
			data = readExcel.getExcelData(filename, "Account");
			
		}catch (final IOException e)
		{
			LOGGER.error(e.getMessage());
			throw e;
		}
			
		return data;
		
	}
}
