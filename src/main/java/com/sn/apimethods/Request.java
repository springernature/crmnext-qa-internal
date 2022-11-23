package com.sn.apimethods;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.Awaitility;
import com.aventstack.extentreports.Status;
import com.sn.config.BaseDriver;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Request {

    private static final Logger LOGGER = LogManager.getLogger(Request.class);

	/***
	 * 
	 * @param uRI
	 * @param stringJSON
	 * @param headerKey
	 * @param headerValue
	 * @author mgp0966
	 * @description: Sends a GET request and waits until the the status code matched expected value OR throws error if timeout triggers
	 * @return Response object
	 */
    
    public static Response getWithWait(String uRI, String headerKey, String headerValue, String paramName, String paramValue, int expStatusCode, int waitInSeconds){
    	
        	
    	//Code
    	//Header header = new Header(headerKey, headerValue);
    	RequestSpecification requestSpecification=RestAssured.given()
    			.contentType(ContentType.JSON)
    			.and().param(paramName, paramValue);
    	//LOGGING
    	BaseDriver.report.log(Status.INFO, "Request Type:GET");
    	BaseDriver.report.log(Status.INFO, "Parameter "+paramName+":"+paramValue);
    	BaseDriver.report.log(Status.INFO, "Request Header "+headerKey +"="+headerValue);
         try {
             Awaitility.await().atMost(waitInSeconds, TimeUnit.SECONDS).until(() -> requestSpecification.get(uRI).getStatusCode() == expStatusCode);
         }
         catch(Exception e)
         {
        	 LOGGER.info(e.getMessage());
         }
        return requestSpecification.get(uRI);
        
    }
    
    /***
	 * 
	 * @param uRI
	 * @param stringJSON
	 * @param headerKey
	 * @param headerValue
	 * @author mgp0966
	 * @description: Sends a post request
	 * @return Response object
	 */
    
    public static Response post(String uRI, String stringJSON, String headerKey, String headerValue){
    	Header header = new Header(headerKey, headerValue);
    	RequestSpecification requestSpecification=RestAssured.given().header(header).and().body(stringJSON);
    	
    	
    	BaseDriver.report.log(Status.INFO, "Request Type:POST");
    	BaseDriver.report.log(Status.INFO, "Json Body:"+stringJSON);
    	BaseDriver.report.log(Status.INFO, "Request Header "+headerKey +"="+headerValue);

        requestSpecification.contentType(ContentType.JSON);
        BaseDriver.report.log(Status.INFO, "Content Type: Json");
        
        return requestSpecification.post(uRI);
        //return response;
    }

    /***
     * @author mgp0966
     * @param uRI
     * @param headerKey
     * @param headerValue
     * @param paramName
     * @param paramValue
     * @return Response
     * @description: Sends a GET request and returns GET response
     */
    public static Response get(String uRI, String headerKey, String headerValue, String paramName, String paramValue){
    	
    	//Code
    	//Header header = new Header(headerKey, headerValue);
    	RequestSpecification requestSpecification=RestAssured.given()
    			.contentType(ContentType.JSON)
    			.and()
    			.param(paramName, paramValue)
    			.and()
    			.log()
    			.all();
    			
    	//LOGGING
    	BaseDriver.report.log(Status.INFO, "Request Type:GET");
    	BaseDriver.report.log(Status.INFO, "Parameter "+paramName+":"+paramValue);
    	BaseDriver.report.log(Status.INFO, "Request Header "+headerKey +"="+headerValue);
         
         //Return statement
        return requestSpecification.get(uRI);
        
    }

    /***
     * @author mgp0966
     * @description sends a PUT request and returns response object
     * @param uRI
     * @param stringJSON
     * @return Response
     */
    public static Response put(String uRI,String stringJSON){
        RequestSpecification requestSpecification=RestAssured.given().body(stringJSON);
        requestSpecification.contentType(ContentType.JSON);
        return requestSpecification.put(uRI);
       
        //return response;
    }
	
    
  
}
