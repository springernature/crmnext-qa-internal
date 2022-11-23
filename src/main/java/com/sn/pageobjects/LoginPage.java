package com.sn.pageobjects;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.sn.config.BaseDriver;
import com.sn.config.Config;
import com.sn.utils.GenericHelper;

public class LoginPage extends BaseDriver{

    private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);

	//WebDriver driver;
		
	@FindBy(name="sap-user")
	WebElement eleUser;
	
	@FindBy(name="sap-password")
	WebElement elePasswd;
	
	@FindBy(id="b1")
	WebElement eleLogon;
	
	@FindBy(id="cuaheader-title")
	WebElement eleHomeTitle;
	
	@FindBy(xpath="//div[@ct='IMG']")
	WebElement eleHomeImage;
	@FindBy(xpath="//iframe[@id='ITSFRAME1']")
	WebElement eleFrame;
	
	@FindBy(id="ur-loading")
	//@FindBy(xpath="//div[@id='ur-loading' and contains(@style,'visibility: visible;')]")
	WebElement eleLoading;
	
	public void login() throws IOException
	{
		
		LOGGER.info("inside method login");
		//driver = getDriver();
		Config config = new Config();
		String username = config.getUserName();
		String password = config.getPassword();
		String url = config.getWebsite();
		
		PageFactory.initElements(driver, this);
		
		driver.get(url);
		GenericHelper operation = new GenericHelper();
		if(!operation.isDisplayed(eleFrame))
		{	
			eleUser.sendKeys(username);
			elePasswd.sendKeys(password);
			eleLogon.click();
			driver.manage().window().maximize();
			waitForElementVisibility(driver, 20, eleFrame);
			driver.switchTo().frame(eleFrame);
			//waitForElementInvisibility(driver, 30, eleLoading);
			waitForElementVisibility(driver, 20, eleHomeImage);
			report.log(Status.PASS, "Logged in Successfully");
		}
		else
		{
			report.log(Status.PASS, "Already Logged in");
		}
		
		

		
	}
}
