package executetests;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;


import com.aventstack.extentreports.Status;
//import com.sn.apt.util.AutoPilotUtil;
import com.sn.config.BaseDriver;
import com.sn.utils.GenericHelper;

public class Salesforce extends BaseDriver {

	//ExtentTest extentTest;
	GenericHelper helper;
	@FindBy(xpath="//img[@title='User']")
	WebElement profileIcon;
	@FindBy(xpath="//a[@title='Leads']")
	WebElement tabLeads;
	@FindBy(xpath="//a[@title='New']")
	WebElement eleNewBtn;
	@FindBy(xpath="//button[@aria-label='Lead Source, --None--']")
	WebElement eleleadSource;
	
	@FindBy(xpath="//button[@aria-label='Lead Status, New']")
	WebElement eleleadStatus;
	
	@FindBy(xpath="//button[@name='salutation']")
	WebElement elesalutation;
	@FindBy(xpath="//input[@name='firstName']")
	WebElement elefirstName;
	@FindBy(xpath="//input[@name='lastName']")
	WebElement elelastName;
	@FindBy(xpath="//input[@name='Company']")
	WebElement eleCompany;
	@FindBy(xpath="//input[@name='Email']")
	WebElement eleEmail;
	
	@FindBy(xpath="//button[@aria-label='Channel Type, --None--']")
	WebElement eleChannelType;
	
	@FindBy(xpath="//button[@aria-label='Customer Type, --None--']")
	WebElement eleCustomerType;
	@FindBy(xpath="//button[@aria-label='Lead Type, --None--']")
	WebElement eleLeadType;
	@FindBy(xpath="//button[@aria-label='Lead Source Team, --None--']")
	WebElement eleLeadSourceTeam;

	@FindBy(xpath="//button[@name='SaveEdit']")
	WebElement eleSave;
	
	@FindBy(xpath="//button[@name='Lead.Accept_Reject']")
	WebElement eleAcceptOrReject;
	
	@FindBy(xpath="//span[text()='Accept']")
	WebElement eleAcceptRadioBtn;
	
	@FindBy(xpath="//button[text()='Next']")
	WebElement eleNextBtn;
	@FindBy(xpath="//button[text()='Finish']")
	WebElement eleFinishBtn;
	
	@FindBy(xpath="//div[text()='Lead']")
	WebElement eleLeadlabel;
	
//	Contacts:	
	
	@FindBy(xpath="//a[@title='Contacts']")
	WebElement tabContacts;
	
	@FindBy(xpath="//input[@placeholder='Search Accounts...']")
	WebElement eleAccountName;
	
	@FindBy(xpath="//button[@aria-label='Job Function, --None--']") 
	WebElement eleJobFunction;
	
	@FindBy(xpath="//div[text()='Contact']")
	WebElement eleContactlabel;

	
//	Accounts:
		
	@FindBy(xpath="//a[@title='Accounts']")
	WebElement tabAccounts;
	
	@FindBy(xpath="//input[@name='Name']")
	WebElement eleAccountNameNewAcc;
	
	
	@FindBy(xpath="//span[text()='Next']/parent::button")
	WebElement eleNextBtnNewAcc;
	
	
	@FindBy(xpath="//input[@aria-label='Invoice Country']")
	WebElement eleinvoiceCountry;
	
	@FindBy(xpath="//label[text()='Invoice Zip/Postal Code']//following-sibling::div/input")
	WebElement eleInvoiceZip;
	
	@FindBy(xpath="//label[text()='Invoice  Street']//following-sibling::div/textarea")
	WebElement eleInvoiceStreet;
	
	@FindBy(xpath="//label[text()='Invoice City']//following-sibling::div/input")
	WebElement eleInvoiceCity;
	
	@FindBy(xpath="//input[@aria-label='Invoice State/Province']")
	WebElement eleInvoiceState;

	@FindBy(xpath="//div[text()='Account']")
	WebElement eleAccountlabel;
	

	
	public void selectSFDropdownValue(WebElement dropdownObject, String Value) throws InterruptedException
	{
		jsScrollToView(dropdownObject);
		Thread.sleep(1000);
		dropdownObject.click();
		Thread.sleep(3000);
		jsScrollToView(driver.findElement(By.xpath("//span[@title='"+Value+"']")));
		driver.findElement(By.xpath("//span[@title='"+Value+"']")).click();
	}
	
	
	
	
	public void createLead(String leadSource,  String salutation,String firstName, String lastName, String company, String email, String channelType, String customerType, String leadType, String leadSourceTeam) throws InterruptedException
	{
		
		PageFactory.initElements(BaseDriver.driver, this);
		driver.manage().window().maximize();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		  waitForElementToBeClickable(driver, 10, tabLeads);
	     executor.executeScript("arguments[0].click();", tabLeads);
	   
		//tabLeads.click();
	    
		waitForElementVisibility(driver, 10, eleNewBtn);
		 
		 
		eleNewBtn.click();
		
		waitForElementVisibility(driver, 10, eleleadSource);
		selectSFDropdownValue(eleleadSource, leadSource);
		selectSFDropdownValue(elesalutation, salutation);
		elefirstName.sendKeys(firstName);
		elelastName.sendKeys(lastName);
		eleCompany.sendKeys(company);
		eleEmail.sendKeys(email);
		
		selectSFDropdownValue(eleChannelType, channelType);
		selectSFDropdownValue(eleCustomerType, customerType);
		selectSFDropdownValue(eleLeadType, leadType);
		selectSFDropdownValue(eleLeadSourceTeam, leadSourceTeam);

		eleSave.click();
		
		//waitForElementWithPollingInterval(driver, 20, eleLeadlabel);
		report.log(Status.PASS, "Lead Created Successfully");
		//AutoPilotUtil.commonLogger("Lead Created Successfully", Status.PASS, Level.INFO);

	}
	
	public void createContact(String salutation, String firstName, String lastName, String email, String accountName, String jobFunction) throws InterruptedException
	{
		PageFactory.initElements(BaseDriver.driver, this);
		driver.manage().window().maximize();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
	     executor.executeScript("arguments[0].click();", tabContacts);
		//tabLeads.click();
	     Thread.sleep(3000);
		//waitForElementVisibility(driver, 10, eleNewBtn);
		eleNewBtn.click();
	     Thread.sleep(3000);
		//waitForElementVisibility(driver, 10, elesalutation);
		selectSFDropdownValue(elesalutation, salutation);
		elefirstName.sendKeys(firstName);
		elelastName.sendKeys(lastName);
		eleEmail.sendKeys(email);
		
		//selectSFDropdownValue(eleAccountName, accountName);
		jsScrollToView(eleAccountName);
		Thread.sleep(1000);
		eleAccountName.click();
		Thread.sleep(3000);
		//jsScrollToView(driver.findElement(By.xpath("//span[@title='"+Value+"']")));
		driver.findElement(By.xpath("//span[@title='"+accountName+"']")).click();
		Thread.sleep(1000);
		
		selectSFDropdownValue(eleJobFunction, jobFunction);
		eleSave.click();
		waitForElementWithPollingInterval(driver, 20, eleContactlabel);
		//eleContactlabel.isDisplayed();
		//AutoPilotUtil.commonLogger("Contact Created Successfully", Status.PASS, Level.INFO);
		report.log(Status.PASS, "Contact Created Successfully");
		
		
	}
	
	public void createAccount(String accountName, String channelType, String customerType, String invoiceCountry, String invoiceZIP, String invoiceCity, String invoiceState) throws InterruptedException
	{
		PageFactory.initElements(BaseDriver.driver, this);
		driver.manage().window().maximize();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
	    executor.executeScript("arguments[0].click();", tabAccounts);
		//tabLeads.click();
	     Thread.sleep(3000);

		waitForElementVisibility(driver, 10, eleNewBtn);
		eleNewBtn.click();
	    Thread.sleep(3000);

		eleNextBtnNewAcc.click();
		
		Thread.sleep(4000);
		eleAccountNameNewAcc.sendKeys(accountName);
		selectSFDropdownValue(eleChannelType, channelType);
		selectSFDropdownValue(eleCustomerType, customerType);
		
		//Address 
		selectSFDropdownValue(eleinvoiceCountry, invoiceCountry);
		eleInvoiceStreet.sendKeys("Magarpatta");
		eleInvoiceZip.sendKeys(invoiceZIP);
		eleInvoiceCity.sendKeys(invoiceCity);
		selectSFDropdownValue(eleInvoiceState, invoiceState);
		eleSave.click();
		waitForElementWithPollingInterval(driver, 20, eleAccountlabel);
		//AutoPilotUtil.commonLogger("Account Created Successfully", Status.PASS, Level.INFO);
		report.log(Status.PASS, "Account Created Successfully");
	}
	
	
	@Test(priority=1)
	public void loginToSalesforce() throws InterruptedException
	{
		PageFactory.initElements(BaseDriver.driver, this);
		driver.get("https://sn-rsm--qa.my.salesforce.com/");
		driver.findElement(By.id("username")).sendKeys("tapan.chikhale@springernature.com.qa");
		Thread.sleep(4000);
		driver.findElement(By.id("password")).sendKeys("Salesforce@6032");
		Thread.sleep(5000);
		driver.findElement(By.id("Login")).click();

		
		waitForElementVisibility(driver, 30, tabLeads);
		report.log(Status.PASS, "Login Successful");
		//AutoPilotUtil.commonLogger("Login Successful", Status.PASS, Level.INFO);
		
	}
	
	
	@Test(dataProvider="LeadData", priority=2)
	public void createNewLead(String leadSource,  String salutation,String firstName, String lastName, String company, String email, String channelType, String customerType, String leadType, String leadSourceTeam) throws InterruptedException
	{
//		String leadSource,  salutation,firstName, lastName, company, email, channelType, customerType, leadType, leadSourceTeam;
//		leadSource="Advertisement";
//				salutation="Mr.";
//				firstName="Test";
//				lastName="User";
//				company="SpringerNature";
//				email="testuser@gtest.com";
//				channelType="Corporate";
//				customerType = "Automotive";
//				leadType="Inbound";
//				leadSourceTeam = "Engagement";
				createLead(leadSource, salutation, firstName, lastName, company, email, channelType, customerType, leadType, leadSourceTeam);
				helper = new GenericHelper();
				//helper.wait(5000);
				helper.isDisplayed(eleAcceptOrReject);
	}
	
	@Test(priority=3, enabled=false)
	public void acceptLead() throws InterruptedException
	{
		Thread.sleep(3000);
		eleAcceptOrReject.click();
		Thread.sleep(2000);
		eleAcceptRadioBtn.click();
		eleNextBtn.click();
		Thread.sleep(1000);
		eleFinishBtn.click();
	}
	
	@Test(dataProvider="ContactData", priority=4, enabled=false)
	public void createNewContact(String salutation, String firstName, String lastName, String email, String accountName, String jobFunction) throws InterruptedException
	{
//		String  salutation,firstName, lastName, email, accountName, jobFunction;
//		salutation="Mr.";
//		firstName="Test";
//		lastName="User";
//		email="testuser@gtest.com";
//		accountName="Spinach";
//		jobFunction = "Data Scientist";		
		lastName = RandomStringUtils.random(8, true, false);
		firstName = RandomStringUtils.random(8, true, false);
		email = firstName+lastName+"@gtest.com";
		createContact(salutation, firstName, lastName, email, accountName, jobFunction);
	}
	
	@Test(dataProvider = "AccountData", priority=5, enabled=false)
	public void createNewAccount(String accountName, String channelType, String customerType, String invoiceCountry, String invoiceZIP, String invoiceCity, String invoiceState) throws InterruptedException
	{
		
//		String accountName, channelType, customerType, invoiceCountry, invoiceZIP, invoiceCity, invoiceState;
//		accountName	= "TestUser";
//		channelType = "Corporate";
//		customerType = "Automotive";
//		invoiceCountry = "India";
//		invoiceZIP = "411012";
//		invoiceCity = "Pune";
//		invoiceState = "Maharashtra";
		createAccount(accountName, channelType, customerType, invoiceCountry, invoiceZIP, invoiceCity, invoiceState);
	}
}
