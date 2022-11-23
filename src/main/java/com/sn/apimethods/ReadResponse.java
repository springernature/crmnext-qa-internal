package com.sn.apimethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
import com.sn.config.BaseDriver;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadResponse {

    private static final Logger LOGGER = LogManager.getLogger(ReadResponse.class);

    /***
     * @author mgp0966
     * @param response
     * @param expectedStatusCode
     * @info: verifies the response status code with expected one
     */
    public void verifyResponseStatusCode(Response response, int expectedStatusCode)
    {
    	try {
    	Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    	
    	BaseDriver.report.log(Status.PASS, "Verified Status Code "+response.getStatusCode());
    	}catch(AssertionError e)
    	{
    		BaseDriver.report.log(Status.FAIL, "Verify Status Code: "+e.getMessage());

    	}
    	
    }
    
    /***
     * @author mgp0966
     * @info verifies the status line
     * @param response
     * @param expectedStatusLine
     */
    public void verifyStatusLine(Response response, String expectedStatusLine)
    {
    	try {
	    	Assert.assertEquals(response.getStatusLine(), expectedStatusLine);
	    	BaseDriver.report.log(Status.PASS, "Verified Status Line: "+response.getStatusLine());
    	}
    	catch(AssertionError e)
    	{
    		BaseDriver.report.log(Status.FAIL, "Verify Status Line:"+e.getMessage());

    	}
    	
    }
    
    /***
     * @author mgp0966
     * @info verifies header item key-value
     * @param response
     * @param Key
     * @param Value
     */
    public void verifyResponseHeader(Response response, String key, String value)
    {
    	try {
    		Assert.assertEquals(response.getHeader(key), value);	
    		BaseDriver.report.log(Status.PASS, "Verified Header item-"+key+response.getHeader(key));
	    }
		catch(AssertionError e)
		{
			BaseDriver.report.log(Status.FAIL, "Verify Header:"+e.getMessage());
		}
    }
	
    /***
     * @author mgp0966
     * @info verifies node values from response
     * @param response
     * @param nodeName
     * @param nodeValue
     * 
     */
    		
    public void verifyNodeValue(Response response, String nodeName, String nodeValue) 
    {
    	try {
	    	JsonPath jsonBody = response.jsonPath();
	    	
	    	Assert.assertEquals(jsonBody.getString(nodeName), nodeValue);
	    	BaseDriver.report.log(Status.PASS, "Verified Node-'"+nodeName+":"+nodeValue+"'");

    	}
  		catch(AssertionError e)
  		{
  			BaseDriver.report.log(Status.FAIL, "Verify Json Node:"+e.getMessage());
  		}
    }
	
    
    /***
     * @author mgp0966
     * @param response
     * @param nodeName
     * @return
     */
    public String getNodeValue(Response response, String nodeName)
    {
	    	JsonPath jsonBody = response.jsonPath();
	    	//AutoPilotUtil.commonLogger(nodeName+": "+jsonBody.getString(nodeName), Status.INFO, Level.INFO);
	    	LOGGER.info(nodeName+": "+jsonBody.getString(nodeName));
	    	return jsonBody.getString(nodeName);

    }
    
    public Map<String, Object> convertJSONObjecttoHashMap(String postJson)
    {
    	Map<String, Object> hashMap = new HashMap<>();
    	
    	JSONObject  json = new JSONObject(postJson);
    	Iterator<String> keys = json.keys();
    	while(keys.hasNext())
    	{
    		String key = keys.next();
    		JSONArray jsonArr = null;
    		if(json.get(key) instanceof JSONObject )
    		{
    			JSONObject value = json.getJSONObject(key);
    			hashMap.putAll(value.toMap());
    		}
    		else if(json.get(key) instanceof String)
    		{
    			hashMap.put(key.toString(), json.get(key));
    		}
    		else if(json.get(key) instanceof Integer)
    		{
    			hashMap.put(key.toString(), json.get(key));
    		}
    		else if(json.get(key) instanceof JSONArray)
    		{
    			jsonArr = json.getJSONArray(key);
    			JSONObject jo = null;
	    		for(int i = 0; i<jsonArr.length();i++)
	    		{
	    			
	    			jo = jsonArr.getJSONObject(i);
	    			hashMap.putAll(jo.toMap());
	    			jo.clear();
	    		}
    		}
    		else
    		{
    			hashMap.put(key.toString(), json.get(key));	
    		}
    	}
    	
    	LOGGER.info(hashMap);
    	
    	return hashMap;    	
    }
    
    

    public Map<String, Object> convertJSONArraytoHashMap(String postJson)
    {
    	Map<String, Object> hashMap = new HashMap<>();
    	try
    	{
    		
	    	JSONArray  json = new JSONArray(postJson);
	    	List<Object> arrList = new ArrayList<>();
	    	arrList.addAll(json.toList());
	    	//Iterator<Object> keys = arrList.iterator();
	    	for (int i = 0; i<json.length();i++)
	    	{
	    		JSONObject jsonObj = (JSONObject) json.get(i);
	    		Iterator<String> jsonKeys = jsonObj.keys();
	        	while(jsonKeys.hasNext())
	        	{
	        		String key = jsonKeys.next();
	        		JSONArray jsonArr = null;
	        		if(jsonObj.get(key) instanceof JSONObject )
	        		{
	        			JSONObject value = jsonObj.getJSONObject(key);
	        			hashMap.putAll(value.toMap());
	        		}
	        		else if(jsonObj.get(key) instanceof String)
	        		{
	        			hashMap.put(key, jsonObj.get(key));
	        		}
	        		else if(jsonObj.get(key) instanceof Integer)
	        		{
	        			hashMap.put(key, jsonObj.get(key));
	        		}
	        		else if(jsonObj.get(key) instanceof JSONArray)
	        		{
	        			jsonArr = jsonObj.getJSONArray(key);
	        			JSONObject jo = null;
	        			for(int j = 0; j<jsonArr.length();j++) 
	    	    		{
	        				if(jsonArr.get(j) instanceof JSONObject)
	    	    			{
		    	    			jo = jsonArr.getJSONObject(j);
		    	    			hashMap.putAll(jo.toMap());
		    	    			jo.clear();
	    	    			}
	        				else
	    	    			{
	    	    				hashMap.put(key, jsonArr.get(j));
	    	    			}
	    	    		}
	        		}
	        		else
	        		{
	        			hashMap.put(jsonObj.toString(), jsonObj.get(key));	
	        		}
	        	}
	    	}
    	}
    	catch(Exception e)
    	{
    		BaseDriver.report.log(Status.FAIL, e.getMessage());
    	}
    	LOGGER.info(hashMap);
    	//AutoPilotUtil.commonLogger(hashMap.toString(), Status.INFO, Level.INFO);
    	return hashMap;  
    }
  

    

}
