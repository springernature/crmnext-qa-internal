package executetests;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class Execution {

	public  WebDriver driver;
	@Test(priority = 0)
	public void printHello()
	{
		System.out.println("Hello world");
		
		driver.get("https://www.toolsqa.com/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	
	}
	
	@Test(priority=1)
	public void assertTitle()
	{
		String title = driver.getTitle();
		System.out.println("title is :"+title);
		Assert.assertEquals(title, "Tools QA");
	}
	
	//@BeforeClass
	public WebDriver getWebdriverInstance()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\GradleWorkspace\\CBSGradleTestNG\\src\\test\\resources\\chromedriver.exe");
	
	driver = new ChromeDriver();
	
	return driver;
	
	
	}
	
	@AfterClass
	public void quitDriver()
	
	{
		driver.quit();
	
	}
	
}
