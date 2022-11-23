package com.sn.pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.sn.config.BaseDriver;
import com.sn.utils.GenericHelper;

public class DisplayPerson extends LoginPage{
	
	//WebDriver driver;
	
    private static final Logger LOGGER = LogManager.getLogger(DisplayPerson.class);

	
	//Personal Information:
	@FindBy(xpath="//input[@title='Business Partner Type']")
	WebElement eleBPType;
	@FindBy(xpath="//input[@title='Title text']")
	WebElement eleTitle;
	@FindBy(xpath="//input[@title='First name of business partner (person)']")
	WebElement eleFirstName;
	@FindBy(xpath="//input[@title='Last name of business partner (person)']")
	WebElement eleLastName;
	@FindBy(xpath="//input[@title='Name 3']")
	WebElement eleName3;
	@FindBy(xpath="//input[@title='Name 2']")
	WebElement eleName2;
	@FindBy(xpath="//input[@title='Name 1 of organization']")
	WebElement eleName1;
	
	@FindBy(xpath="//input[@title='Institute I']")
	WebElement eleInstitute1;
	@FindBy(xpath="//input[@title='Nickname of Business Partner (Person)']")
	WebElement eleInstitute2;
	@FindBy(xpath="//input[@title='Academic Title: Written Form']")
	WebElement eleAcademicTitle;
	@FindBy(xpath="//input[@title='Business partner: Language']")
	WebElement eleLanguage;
	@FindBy(xpath="//input[@title='Street Name']")
	
	//Address Fields: 
	WebElement eleStreet;
	@FindBy(xpath="//input[@title='Room or Apartment Number']")
	WebElement eleRoom;
	@FindBy(xpath="//input[@title='Floor in building']")
	WebElement eleFloor;
	@FindBy(xpath="//input[@title='Street 2']")
	WebElement eleStreet2;
	@FindBy(xpath="//input[@title='House number supplement']")
	WebElement eleHouseNumSupplement;
	@FindBy(xpath="//input[@title='District']")
	WebElement eleDistrict;
	@FindBy(xpath="//input[@title='Region']")
	WebElement eleRegion;
	@FindBy(xpath="//input[@title='Street Address Undeliverable Flag']")
	WebElement eleUndeliverable;
	@FindBy(xpath="//input[@title='Building (Number or Code)']")
	WebElement eleBuildingCode;
	@FindBy(xpath="//input[@title='House Number']")
	WebElement eleHouseNumber;
	@FindBy(xpath="//input[@title='Postal Code']")
	WebElement elePostalCode;
	@FindBy(xpath="//input[@title='City']")
	WebElement eleCity;
	@FindBy(xpath="//input[@title='Country Key']")
	WebElement eleCountry;
	
	//Communication fields:
	@FindBy(xpath="//input[@title='First telephone no.: dialling code+number']")
	WebElement eleTelephone;
	@FindBy(xpath="//input[@title='E-Mail Address']")
	WebElement eleEmail;

	
	//Tab Identification fields:
	@FindBy(xpath="//input[@title='Business Partner Number in External System']")
	WebElement eleExternalBPNumber;
	@FindBy(xpath="//th[@title='Identification Type']//ancestor::tr[@role='row']//following-sibling::tr//input[contains(@id,'[1,1]_c')]")
	WebElement eleIDType;
	@FindBy(xpath="//th[@title='Identification Type']//ancestor::tr[@role='row']//following-sibling::tr//input[contains(@id,'[1,3]_c')]")
	WebElement eleIdentificationNumber;
	@FindBy(xpath="//th[@title='Tax Number']//ancestor::tr[@role='row']//following-sibling::tr//input[contains(@id,'[1,3]_c')]")
	WebElement eleVATNumber; 
		
	//Sales Customer Details:
	@FindBy(xpath="//div[@title='Role Detail']")
	WebElement eleRoleDetail; 
	@FindBy(xpath="//input[@title='BP Role for Screen Usage']//parent::td//following-sibling::td/span")
	WebElement eleExpandDisplayInBPRole; 
	
	@FindBy(xpath="//*[text()='Sales Customer']")
	WebElement eleSelectSalesCustomerRole; 
	@FindBy(xpath="//*[text()='Business Partner (Gen.)']")
	WebElement eleSelectBusinessPartnerRole; 
	
	
	@FindBy(xpath="//div[@title='More' and @class='lsToolbar--standards-overflowButton lsToolbar--standards-overflowButton-active lsToolbar--standards-overflowButton-hoverable']")
	WebElement eleMoreMenuOptions;
	
	@FindBy(xpath="//input[@title='2nd level']")
	WebElement eleSecondLevel;	
	
	//Sales Org Details
	@FindBy(xpath="//span[text()='Sales and Distribution']")
	WebElement eleSalesAndDistribution;
	
	@FindBy(xpath="//div[@title='There are sales areas for this customer that can be changed']")
	WebElement eleSalesAreas;
	
	@FindBy(xpath="//div[contains(@lsdata,'Cancel')]")
	WebElement eleCancel;
	

	@FindBy(xpath="//div[@title='General Data (Ctrl+F1)']")
	WebElement eleGeneralDataMenu;
	@FindBy(xpath="//div[@title='Sales and Distribution (Ctrl+F3)']")
	WebElement eleSalesAndDistributionMenu;
	
	@FindBy(id="ur-loading")
	WebElement eleLoading;
	
	
	public void verifyBPDetailsInMQ2(Map<String, Object> hashMap) throws InterruptedException, AssertionError {
		
		LOGGER.info("inside method verifyBPDetailsInMQ2");
		PageFactory.initElements(BaseDriver.driver, this);
		
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("ITSFRAME1");
		
		GenericHelper oValues = new GenericHelper();
		waitForElementInvisibility(driver, 30, eleLoading);
		
		if(hashMap.get("isNotSalesCustomer").toString().equals("false"))
		{
			switchToSalesCustomerRole();
			Thread.sleep(3000);	
			
		}
	/*	
		
		String bpType 			= oValues.readValueFromInput(eleBPType);
		String bpTitle 			= oValues.readValueFromInput(eleTitle);
		String secondLevel 		= null;
		if(eleSecondLevel.isDisplayed())
		{
			secondLevel			= oValues.readValueFromInput(eleSecondLevel);
		}		
		String firstName 		= oValues.readValueFromInput(eleFirstName);
		String lastName 		= oValues.readValueFromInput(eleLastName);
		String institute1 		= oValues.readValueFromInput(eleInstitute1);
		String institute2 		= oValues.readValueFromInput(eleInstitute2);
		String academicTitle 	= oValues.readValueFromInput(eleAcademicTitle);
		String street 			= oValues.readValueFromInput(eleStreet);
		String houseNumber 		= oValues.readValueFromInput(eleHouseNumber);
		String postalCode 		= oValues.readValueFromInput(elePostalCode);
		String city 			= oValues.readValueFromInput(eleCity);
		String country	 		= oValues.readValueFromInput(eleCountry);
		String telephone 		= oValues.readValueFromInput(eleTelephone);
		String email	 		= oValues.readValueFromInput(eleEmail);
	*/	
		Thread.sleep(3000);
		waitForElementInvisibility(driver, 30, eleLoading);
		switchTab("Identification");
		waitForElementInvisibility(driver, 30, eleLoading);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("ITSFRAME1");
		Thread.sleep(3000);
		String externalBPID	 	= oValues.readValueFromInput(eleExternalBPNumber);
		String idType	 		= oValues.readValueFromInput(eleIDType);
		String idNumber	 		= oValues.readValueFromInput(eleIdentificationNumber);
		String vatNumber		= oValues.readValueFromInput(eleVATNumber);
		
		switchTab("Address");
		waitForElementInvisibility(driver, 30, eleLoading);
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("ITSFRAME1");
		
		//Verify Input JSON data with SAP UI BP details
		//Actions action = new Actions(driver);
		List<String> arrBpSalesOrgs = null;
		List<String> arrBpSalesDivs = null;
		
		for(Map.Entry<String,Object> items:hashMap.entrySet())
		{
			if(!items.getValue().toString().equals(""))
			{
	
	
				switch(items.getKey())
				{
				case "firstName":
					Assert.assertEquals(oValues.readValueFromInput(eleFirstName), items.getValue(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "lastName":
					Assert.assertEquals(oValues.readValueFromInput(eleLastName), items.getValue(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "name1":
					Assert.assertEquals(oValues.readValueFromInput(eleName1), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "name2":
					Assert.assertEquals(oValues.readValueFromInput(eleName2), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "name3":
					Assert.assertEquals(oValues.readValueFromInput(eleName3), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "title":
						if(items.getValue().equals("1"))
						{
							Assert.assertEquals(oValues.readValueFromInput(eleTitle), "Mr.",items.getKey());
							report.log(Status.PASS, items.getKey()+" Verified");
						}
						else if(items.getValue().equals("2"))
						{
							Assert.assertEquals(oValues.readValueFromInput(eleTitle), "Ms.",items.getKey());
							report.log(Status.PASS, items.getKey()+" Verified");
						}
						else if(items.getValue().equals("3"))
						{
							Assert.assertEquals(oValues.readValueFromInput(eleTitle), "Mr./Ms.",items.getKey());
							report.log(Status.PASS, items.getKey()+" Verified");
						}
			
					break;
					
				case "academicTitle":
					if(items.getValue().equals("DR"))
					{
						Assert.assertEquals(oValues.readValueFromInput(eleAcademicTitle), "Dr.",items.getKey());
						report.log(Status.PASS, items.getKey()+" Verified");
					}
					else
					{
						Assert.assertEquals(oValues.readValueFromInput(eleAcademicTitle), items.getValue().toString(),items.getKey());
						report.log(Status.PASS, items.getKey()+" Verified");
					}
					
					break;	
					
				case "organization":
					Assert.assertEquals(oValues.readValueFromInput(eleInstitute1), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "department":
					Assert.assertEquals(oValues.readValueFromInput(eleInstitute2), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "street":
					Assert.assertEquals(oValues.readValueFromInput(eleStreet), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "houseNum":
					Assert.assertEquals(oValues.readValueFromInput(eleHouseNumber), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "postalCode":
					Assert.assertEquals(oValues.readValueFromInput(elePostalCode), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "city":
					Assert.assertEquals(oValues.readValueFromInput(eleCity), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "country":
					
					Assert.assertEquals(oValues.readValueFromInput(eleCountry), items.getValue().toString(),items.getKey());

					//Read BP Sales Area
					openSalesAreas();
					waitForElementInvisibility(driver, 30, eleLoading);
					//Thread.sleep(3000);
					eleSalesAreas.click();
					//Thread.sleep(2000);
					waitForElementInvisibility(driver, 30, eleLoading);
					arrBpSalesOrgs = getSalesOrgs();
					arrBpSalesDivs = getSalesOrgDivisions();
					
					report.log(Status.PASS, items.getKey()+" Verified");
					verifySalesAreas(items.getKey(), items.getValue().toString(), arrBpSalesOrgs, arrBpSalesDivs);
					report.log(Status.PASS, items.getKey()+"-Sales Area Verified");

					eleCancel.click();
					waitForElementVisibility(driver, 5, eleSalesAreas);
					//Thread.sleep(5000);
					waitForElementInvisibility(driver, 30, eleLoading);
					switchToBusinessPartnerRole();
					waitForElementVisibility(driver, 5, eleBPType);
					break;
					
				case "telephone":
					Assert.assertEquals(oValues.readValueFromInput(eleTelephone), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "email":
					Assert.assertEquals(oValues.readValueFromInput(eleEmail), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
				case "bpType":
					Assert.assertEquals(oValues.readValueFromInput(eleBPType), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "secondLevel":
					switchToSalesCustomerRole();
					Assert.assertEquals(oValues.readValueFromInput(eleSecondLevel), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "vatRegistration":
					Assert.assertEquals(vatNumber, items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "bpIdentificationType":
					Assert.assertEquals(idType, items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "bpIdentificationNumber":
					System.out.println("---------------------------------------------");
					System.out.println("UI Value: "+idNumber);
					System.out.println("Json value: "+items.getValue().toString());
					System.out.println(items.getValue());
					Assert.assertEquals(idNumber, items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "additionalSalesArea":

					verifySalesAreas(items.getKey(), items.getValue().toString(), arrBpSalesOrgs, arrBpSalesDivs );
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "building":

						Assert.assertEquals(oValues.readValueFromInput(eleBuildingCode), items.getValue().toString(),items.getKey());
						report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "room":	
					Assert.assertEquals(oValues.readValueFromInput(eleRoom), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "floor":	
					Assert.assertEquals(oValues.readValueFromInput(eleFloor), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "streetAdditional":	
					Assert.assertEquals(oValues.readValueFromInput(eleStreet2), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;
					
				case "houseNumAdditional":	
					Assert.assertEquals(oValues.readValueFromInput(eleHouseNumSupplement), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;			
					
				case "district":	
					Assert.assertEquals(oValues.readValueFromInput(eleDistrict), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "region":	
					Assert.assertEquals(oValues.readValueFromInput(eleRegion), items.getValue().toString(),items.getKey());
					report.log(Status.PASS, items.getKey()+" Verified");
					break;	
					
				case "addressOverruled":
					if(items.getValue().toString().equals("true"))
					{
						Assert.assertEquals(oValues.readValueFromInput(eleUndeliverable), "Poor Address but OK",items.getKey());
						report.log(Status.PASS, items.getKey()+" Verified");
					}	
					break;
		
				case "isNotSalesCustomer":
					if(items.getValue().toString().equals("true"))
					{
						eleExpandDisplayInBPRole.click();
						Assert.assertEquals(oValues.isDisplayed(eleSelectSalesCustomerRole), false,items.getKey());
						report.log(Status.PASS, items.getKey()+" Verified");
					}
					else
					{
						eleExpandDisplayInBPRole.click();
						Assert.assertEquals(oValues.isDisplayed(eleSelectSalesCustomerRole), true,items.getKey());
						report.log(Status.PASS, items.getKey()+" Verified");
					}
						
					break;	
					
					
				default:
					report.log(Status.INFO, items.getKey()+" is NOT Verified");
					break;	
					
				}
				
				
			}
			else
			{
				report.log(Status.PASS, items.getKey()+" was Blank or empty in JSON hence NOT Verified");
			}
			
			
			
		}
		
		
	}
	
	public void switchTab(String tabName)
	{
		LOGGER.info("inside method switchTab");
		
		try {
			
			driver.findElement(By.xpath("//div[text()='"+tabName+"' and @action='TAB_ACCESS'][1]")).click();
		}
		catch(NoSuchElementException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	public void switchToSalesCustomerRole() throws InterruptedException
	{
		LOGGER.info("inside method switchToSalesCustomerRole");
		
		try {
						
			eleExpandDisplayInBPRole.click();
			eleSelectSalesCustomerRole.click();
			waitForElementInvisibility(driver, 30, eleLoading);
			}
			catch(NoSuchElementException e)
			{
				System.out.println(e.getMessage());
				report.log(Status.FAIL, e.getMessage());
			}
	}
	
	public void switchToBusinessPartnerRole()
	{
		LOGGER.info("inside method switchToBusinessPartnerRole");
		
		try {
						
			driver.findElement(By.xpath("//input[@title='BP Role for Screen Usage']")).click();
			eleSelectBusinessPartnerRole.click();
			}
			catch(NoSuchElementException e)
			{
				System.out.println(e.getMessage());
				report.log(Status.FAIL, e.getMessage());
			}
	}
	
	
	public void openSalesAreas()
	{
		LOGGER.info("inside method openSalesAreas");
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).sendKeys(Keys.F3).keyUp(Keys.CONTROL).perform();
		//action.sendKeys(Keys.F3);
		//action.build().perform();
	}
	
	
	public List<String> getSalesOrgs() {
		
		LOGGER.info("inside method getSalesOrgs");
		
		List<WebElement> arrElesalesOrgs = driver.findElements(By.xpath("//input[contains(@lsdata,'SALES_ORG[')]"));
		ArrayList<String> arrSalesOrgs = new ArrayList<>();
		if(!arrElesalesOrgs.isEmpty())
		{
			for(WebElement orgs:arrElesalesOrgs)	
			{
				arrSalesOrgs.add(orgs.getAttribute("value"));
			}
		}
		
		return arrSalesOrgs;
	}
	
	public List<String> getSalesOrgDivisions() {
		
		LOGGER.info("inside method getSalesOrgDivisions");
		
		List<WebElement> arrElesaleDiv = driver.findElements(By.xpath("//input[contains(@lsdata,'DIVISION[')]"));
		ArrayList<String> arrSalesDivision = new ArrayList<>();
		if(!arrElesaleDiv.isEmpty())
		{
			for(WebElement divs:arrElesaleDiv)	
			{
				arrSalesDivision.add(divs.getAttribute("value"));
			}
		}
		
		return arrSalesDivision;
	}
	
	
	public void verifySalesAreas(String key, String value, List<String>bpSalesOrgs, List<String>bpSalesDivs)
	{
		
		LOGGER.info("inside method verifySalesAreas");
		
		if(key.equals("country") && value.equals("DE"))
		{
			Assert.assertTrue(bpSalesOrgs.toString().contains("0293"), "Sales Org");
			Assert.assertTrue(bpSalesDivs.toString().contains("01"), "Sales Org Division");
			Assert.assertTrue(bpSalesDivs.toString().contains("02"), "Sales Org Division");
			Assert.assertTrue(bpSalesDivs.toString().contains("03"), "Sales Org Division");
			Assert.assertTrue(bpSalesDivs.toString().contains("30"), "Sales Org Division");
			
		}else if(key.equals("country") && value.equals("US"))
		{
			Assert.assertTrue(bpSalesOrgs.toString().contains("1145"), "Sales org");
			Assert.assertTrue(bpSalesDivs.toString().contains("01"), "Sales Org Division");
			Assert.assertTrue(bpSalesDivs.toString().contains("02"), "Sales Org Division");
			Assert.assertTrue(bpSalesDivs.toString().contains("03"), "Sales Org Division");
			Assert.assertTrue(bpSalesDivs.toString().contains("30"), "Sales Org Division");
		}
		else if(key.equals("additionalSalesArea"))
		{
			String expSalesOrg = value.substring(0, 3);	//11450101
			String expSalesDiv = value.substring(6, 7);
			Assert.assertTrue(bpSalesOrgs.toString().contains(expSalesOrg),"Additional Sales org");
			Assert.assertTrue(bpSalesDivs.toString().contains(expSalesDiv),"Additional Sales org division");
		}
		
	}
	
	//input[contains(@lsdata,'SALES_ORG[')]
	
}

//
