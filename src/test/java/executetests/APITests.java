package executetests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sn.apimethods.ReadResponse;
import com.sn.apimethods.Request;
import com.sn.config.BaseDriver;
import com.sn.dataproviders.TestDataFromExcel;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APITests extends BaseDriver{

	
	@Test(enabled=false)
	public void createAndVerifyOrder()
	{
		String URI = "https://cbs-order-service-staging.springernature.app/sales-order";
		String strJson = "{\r\n"
				+ "    \"externalOrderID\": \"12345\",\r\n"
				+ "    \"distributionChannel\": \"01\",\r\n"
				+ "    \"organizationDivision\": \"00\",\r\n"
				+ "    \"salesOrderType\": \"YWS0\",\r\n"
				+ "    \"salesOrganization\": \"0293\",\r\n"
				+ "    \"soldToParty\": \"3001479242\",\r\n"
				+ "    \"salesOrderItem\": [\r\n"
				+ "        {\r\n"
				+ "        \"material\": \"978-3-540-78727-3\",\r\n"
				+ "        \"requestedQuantity\": 2,\r\n"
				+ "        \"salesOrderItem\": 10,\r\n"
				+ "        \"salesOrderItemCategory\": \"YBT1\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		
		//Extent Report test started
		//ExtentTestManager.startTest("Create Order");
		ExtentTest report = BaseDriver.getExtentReportObject("Create Order");
		//Send Post Request:
		Response response = Request.post(URI, strJson, "x-tenant-id", "NETWEBSHOP");
		report.log(Status.INFO, "POST Request Sent.");
		
		//Read POST response:
		ReadResponse resp = new ReadResponse();
		
		//Verify Status Code:
		resp.verifyResponseStatusCode(response, 202);
		
		//Verify Response Message:
		resp.verifyNodeValue(response, "message", "order is accepted.");
		
		//Logs for Debugging:
		System.out.println(response.getStatusCode());
		System.out.println(response.getStatusLine());
		System.out.println(response.getContentType());
		//System.out.println(response.getHeaders().toString());
		System.out.println(resp.getNodeValue(response, "orderUuid"));
		
		//End of Test
		BaseDriver.reporter.flush();

	}
	
//********************************************************************************************************************************	
	@Test(enabled=false)
	public void createBP()

	{
		
		//Initialize/Define variables: 
		String uRI = "https://contract-service-test.springernature.app/partner";
		String strJson = "{\"person\":\r\n"
				+ "  { \"firstName\":\"Haris\",\r\n"
				+ "    \"lastName\": \"Wells\",\r\n"
				+ "    \"title\": \"1\",\r\n"
				+ "    \"academicTitleAdditional\": \"PR\",\r\n"
				+ "    \"academicTitle\": \"DR\",\r\n"
				+ "    \"organization\": \"University of Washington\",\r\n"
				+ "    \"department\": \"Research\"\r\n"
				+ "   },\r\n"
				+ "	\r\n"
				+ " \"streetAddress\": {\r\n"
				+ "    \"building\": \"\",\r\n"
				+ "    \"room\": \"\",\r\n"
				+ "    \"floor\": \"\",\r\n"
				+ "    \"street\": \"Grabengasse\",\r\n"
				+ "    \"houseNum\": \"1\",\r\n"
				+ "    \"streetAdditional\": \"\",\r\n"
				+ "    \"houseNumAdditional\": \"\",\r\n"
				+ "    \"district\": \"\",\r\n"
				+ "    \"postalCode\": \"69117\",\r\n"
				+ "    \"city\": \"Heidelberg\",\r\n"
				+ "    \"region\": \"\",\r\n"
				+ "    \"country\": \"DE\",\r\n"
				+ "    \"addressOverruled\":false\r\n"
				+ " },\r\n"
				+ "\"communication\":\r\n"
				+ "   {\r\n"
				+ "      \"telephone\":\"778758\",\r\n"
				+ "      \"email\":\"harry.wells@starlabs.com\"\r\n"
				+ "    },\r\n"
				+ "    \"isAuthor\": false,\r\n"
				+ "    \"isBillTo\":true,\r\n"
				+ "    \"isShipTo\": true,\r\n"
				+ "    \"isCompanyContact\":false,\r\n"
				+ "    \"additionalSalesArea\": \"02930101\",\r\n"
				+ "    \"isNotSalesCustomer\":false,\r\n"
				+ "    \"bpType\": \"0001\",\r\n"
				+ "    \"secondLevel\": \"IC\",\r\n"
				+ "     \"identifications\":\r\n"
				+ "     [{\r\n"
				+ "        \"bpIdentificationType\": \"ZTHINK\",\r\n"
				+ "        \"bpIdentificationNumber\": \"1234567\"\r\n"
				+ "     }\r\n"
				+ "		 ]\r\n"
				+ "}\r\n";
		
		
		//Start Extent Reports
		//ExtentTestManager.startTest("Create BP");
		ExtentTest report = BaseDriver.getExtentReportObject("Create BP");
		//Send POST request:
		Response response = Request.post(uRI, strJson, "x-tenant-id", "NETWEBSHOP");
		
		//Read response:
		ReadResponse resp = new ReadResponse();
		
		//Verify Status Code: 
		resp.verifyResponseStatusCode(response, 200);
		
		//Verify Status Message:
		resp.verifyNodeValue(response, "message", "BP create request is submitted successfully");
		
		//Logs:
		System.out.println("iLid is:"+resp.getNodeValue(response, "iLid"));
		report.log(Status.PASS, "BP Created Successfully.");
		
		//End Test
		BaseDriver.reporter.flush();;
	}
	
//********************************************************************************************************************************	
	
	@Test(dataProvider="CreateBP", enabled=false)
	public String CreateAndVerifyBP(String URI, String x_tenant_id, String jsonBody) throws InterruptedException, IOException
	{
		
		//ReadExcelData readXLdata = new ReadExcelData();
		//Initialize/Define variables:
		String postURI = URI;
		String iLid, BP = null;
		String getURI = URI;
		String xTenantId= x_tenant_id;
		String postJson = jsonBody;
	/**
	 * 			
	 
				"{\"person\":\r\n"
				+ "  { \"firstName\":\"Haris\",\r\n"
				+ "    \"lastName\": \"Wells\",\r\n"
				+ "    \"title\": \"1\",\r\n"
				+ "    \"academicTitleAdditional\": \"PR\",\r\n"
				+ "    \"academicTitle\": \"DR\",\r\n"
				+ "    \"organization\": \"University of Washington\",\r\n"
				+ "    \"department\": \"Research\"\r\n"
				+ "   },\r\n"
				+ "	\r\n"
				+ " \"streetAddress\": {\r\n"
				+ "    \"building\": \"\",\r\n"
				+ "    \"room\": \"\",\r\n"
				+ "    \"floor\": \"\",\r\n"
				+ "    \"street\": \"Grabengasse\",\r\n"
				+ "    \"houseNum\": \"1\",\r\n"
				+ "    \"streetAdditional\": \"\",\r\n"
				+ "    \"houseNumAdditional\": \"\",\r\n"
				+ "    \"district\": \"\",\r\n"
				+ "    \"postalCode\": \"69117\",\r\n"
				+ "    \"city\": \"Heidelberg\",\r\n"
				+ "    \"region\": \"\",\r\n"
				+ "    \"country\": \"DE\",\r\n"
				+ "    \"addressOverruled\":false\r\n"
				+ " },\r\n"
				+ "\"communication\":\r\n"
				+ "   {\r\n"
				+ "      \"telephone\":\"778758\",\r\n"
				+ "      \"email\":\"harry.wells@starlabs.com\"\r\n"
				+ "    },\r\n"
				+ "    \"isAuthor\": false,\r\n"
				+ "    \"isBillTo\":true,\r\n"
				+ "    \"isShipTo\": true,\r\n"
				+ "    \"isCompanyContact\":false,\r\n"
				+ "    \"additionalSalesArea\": \"02930101\",\r\n"
				+ "    \"isNotSalesCustomer\":false,\r\n"
				+ "    \"bpType\": \"0001\",\r\n"
				+ "    \"secondLevel\": \"IC\",\r\n"
				+ "     \"identifications\":\r\n"
				+ "     [{\r\n"
				+ "        \"bpIdentificationType\": \"ZTHINK\",\r\n"
				+ "        \"bpIdentificationNumber\": \"1234567\"\r\n"
				+ "     }\r\n"
				+ "		 ]\r\n"
				+ "}\r\n";
		*/
		
		//Start Extent Reports
		//ExtentTestManager.startTest("Create and Verify BP");
		ExtentTest report = BaseDriver.getExtentReportObject("Create and Verify BP");
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
		report.log(Status.PASS, "BP Created Successfully.");
		
		Thread.sleep(15000);
		
		//Send GET request:
		Response getResp = Request.get(getURI, "x-tenant-id", xTenantId, "iLid", iLid);
		
		//Verify GET response status code:
		readResp.verifyResponseStatusCode(getResp, 200);
		
		//Verify Response body:
		String respJson = getResp.getBody().asString();
		report.log(Status.INFO, respJson);	
	/*	
		Map<String, Object> mapPost =readResp.convertJSONObjecttoHashMap(postJson);		
		Set<String> oSet = mapPost.keySet();
		Iterator<String> itr = oSet.iterator();
		
		while(itr.hasNext())
		{
			String nodeName = itr.next();
			System.out.println(nodeName);
			System.out.println(mapPost.get(nodeName).toString());
			String nodeValue = mapPost.get(nodeName).toString();
			//readResp.verifyNodeValue(getResp, nodeName, nodeValue);
			
			if(respJson.contains(nodeName) && respJson.contains(nodeValue))
			{
				AutoPilotUtil.commonLogger("Verified "+nodeName, Status.PASS, Level.INFO);	
			}
			else
			{
				System.out.println("Failed "+nodeName);
				AutoPilotUtil.commonLogger("Failed to Verify "+nodeName, Status.FAIL, Level.INFO);	
			}
		}
	*/
		
		Map<String, Object> mapPost =readResp.convertJSONArraytoHashMap(respJson);		

		BP = mapPost.get("businessPartner").toString();
		report.log(Status.INFO, BP);
		System.out.println("BPID: "+BP);
		
		
		
	
		//End Test
		
		//return BP;
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//LoginPage loginPage = new LoginPage();
		//loginPage.login();
		BaseDriver.reporter.flush();
		return BP;
		
		
		
	}
	
	@Test
	public void getBP() {
		ExtentTest report = BaseDriver.getExtentReportObject("BP");
		String URL = "https://contract-service-test.springernature.app/internal/partner";
		String ilid = "WS0000028446";
		String apikey = "baaa925b-390e-4edf-b4e6-d7a5d7000d5d";
		
		Response resp = Request.get(URL, "apikey", apikey, "il-id", ilid);
		SoftAssert verify = new SoftAssert();
		verify.assertEquals(resp.getStatusCode(), 200);
		verify.assertEquals(resp.getStatusCode(), 400);
		JsonPath jpath = resp.jsonPath();
		if(resp.getBody().asString().charAt(0) =='['){
			JSONArray body = new JSONArray(resp.getBody().asString());
			//System.out.println(body[0]);
		}else
		{
			JSONObject body = new JSONObject(resp.getBody().asString());
		}
		
		
		
		System.out.println((String)jpath.get("$[0].BUT000.businesspartner"));
		System.out.println(resp.asString());
		//verify.assertAll();
	}
	
	
}
