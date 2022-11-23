package com.sn.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.sn.config.BaseDriver;

public class MaintainBP extends LoginPage{
	
    private static final Logger LOGGER = LogManager.getLogger(MaintainBP.class);

	//WebDriver driver;
	@FindBy(xpath="//input[@title='Business Partner Number']")
	WebElement eleBusinessPartner;
	
	@FindBy(id="cuaheader-title")
	WebElement eleHomeTitle;


	public void searchBP(String BP) throws InterruptedException {
		
		LOGGER.info("inside method searchBP");
		
		PageFactory.initElements(BaseDriver.driver, this);
		
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("ITSFRAME1");
		eleBusinessPartner.sendKeys(BP);
		eleBusinessPartner.sendKeys(Keys.ENTER);
		report.log(Status.INFO, "BP ID entered and pressed ENTER");
		
	}
	
}

//
