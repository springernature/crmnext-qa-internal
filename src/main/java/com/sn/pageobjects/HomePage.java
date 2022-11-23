package com.sn.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.sn.config.BaseDriver;

public class HomePage extends LoginPage{
	
	
    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

	//WebDriver driver;
	@FindBy(xpath="//*[@id='ToolbarOkCode']")
	WebElement eleTCode;
	
	@FindBy(id="cuaheader-title")
	WebElement eleHomeTitle;


	public void searchTCode(String TCode) {
		
		LOGGER.info("inside method searchTCode");
		PageFactory.initElements(BaseDriver.driver, this);
		
		//Thread.sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("ITSFRAME1");
		System.out.println("HOmePageExecuted");
		//waitForElementVisibility(super.driver, 10, eleHomeTitle);
		eleTCode.sendKeys(TCode);
		eleTCode.sendKeys(Keys.ENTER);
		
	}
	
}

////input[@title='Business Partner Number']
