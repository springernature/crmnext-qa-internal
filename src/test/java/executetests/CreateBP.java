package executetests;

import java.io.IOException;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.sn.apimethods.ReadResponse;
import com.sn.apimethods.Request;
import com.sn.config.BaseDriver;
import com.sn.pageobjects.DisplayPerson;
import com.sn.pageobjects.HomePage;
import com.sn.pageobjects.LoginPage;
import com.sn.pageobjects.MaintainBP;
import io.restassured.response.Response;

public class CreateBP extends BaseDriver{
	
    private static final Logger LOGGER = LogManager.getLogger(CreateBP.class);

	public static String BP = null;
	//ReadXML xml = new ReadXML();
		
	/***
	 * @author mgp0966
	 * @description This method creates BP using POST API and 
	 * verifies in the sap web that the BP is created successfully
	 * @dataProvider CreateBP
	 * @param URI
	 * @param x_tenant_id
	 * @param jsonBody
	 * @throws InterruptedException
	 * @throws IOException
	 */
	//@Test(dataProvider="BPData", priority=1)
	@Test(dataProvider="BPTestData", priority=1)
	public void CreateAndVerifyBP(String URI, String x_tenant_id, String jsonBody) throws InterruptedException, IOException

	{
		LOGGER.info("inside method CreateAndVerifyBP");
		//Initialize/Define variables:
		String postURI = URI;
		String getURI = URI;
		String xTenantId= x_tenant_id;
		String iLid;
		String postJson = jsonBody;
		
		//Send Post request:
		Response postResponse = Request.post(postURI, postJson, "x-tenant-id", xTenantId);
		
		//Read response:
		ReadResponse readResp = new ReadResponse();
		
		//Verify status code:
		readResp.verifyResponseStatusCode(postResponse, 200);
		
		//Verify Status Message
		readResp.verifyNodeValue(postResponse, "message", "BP create request is submitted successfully");
		
		//Capture response data i.e. iLid :
		System.out.println("iLid is:"+readResp.getNodeValue(postResponse, "iLid"));
		iLid = readResp.getNodeValue(postResponse, "iLid");	
		
		//Send GET request:
		int waitCounter = 0;
		Response getResp = Request.get(getURI, "x-tenant-id", xTenantId, "iLid", iLid);
		
		while( waitCounter<=20 && getResp.getStatusCode()!=200)
		{
			Thread.sleep(2000);
			getResp = Request.get(getURI, "x-tenant-id", xTenantId, "iLid", iLid);
			waitCounter++;
		}
		
		//Verify GET response status code:
		readResp.verifyResponseStatusCode(getResp, 200);
		
		//Verify Response body:
		String respJson = getResp.getBody().asString();
		report.log(Status.INFO, "GET RESPONSE: "+respJson);	
		Map<String, Object> mapPost =readResp.convertJSONArraytoHashMap(respJson);		

		/***
		 * Capture BP ID from get response and do further validations accordingly---->
		 */
		
		if(mapPost.containsKey("businessPartner"))
		{
			BP = mapPost.get("businessPartner").toString();
			report.log(Status.INFO, BP);	
			
			/**
			 * SAP WEB PART STARTS HERE:
			 */
			
			LoginPage loginPage = new LoginPage();
			loginPage.login();
			
			//Open Transaction in SAP
			HomePage homePage = new HomePage();
			homePage.searchTCode("BP");
			
			//Search BP on Maintain BP Screen
			MaintainBP maintainBP = new MaintainBP();
			System.out.println(BP);
			maintainBP.searchBP(BP);
			
			//Read BP fields and verify with POST JSON data
			DisplayPerson dp = new DisplayPerson();
			Map<String, Object> hashmapPostJson =readResp.convertJSONObjecttoHashMap(postJson);
			dp.verifyBPDetailsInMQ2(hashmapPostJson);
			
		}
		else
		{
			report.log(Status.FAIL, "Node 'businessPartner' not found in JSON response");
		}
			
		System.out.println("BPID: "+BP);
	
	}

}
