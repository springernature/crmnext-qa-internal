package com.sn.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.sn.dataproviders.TestDataFromExcel;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseDriver extends TestDataFromExcel
{
	
	private static final Logger LOGGER = LogManager.getLogger(BaseDriver.class);
	public static final String USERDIR = System.getProperty("user.dir");
	
	public static final String PROPFILENAME = USERDIR+"\\src\\main\\resources\\config.properties";
	//public static ExtentReports extent;
	//public static ExtentTest test;
	//public Excel_reader excelreader;
	//ExtentReporter reporter;
	public static ExtentReports reporter;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest report;
	public ITestResult result;
	public static WebDriver driver;

	/***
	 * @author mgp0966
	 * @info read properties file
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Properties readPropertiesFile(String fileName) throws IOException {
		Properties  prop = null;
      try (
    	  FileInputStream fis = new FileInputStream(fileName);
    	  
    	)
         {
    	  prop = new Properties();
    	  prop.load(fis);
         
         
      } catch(FileNotFoundException fnfe) {
         fnfe.printStackTrace();
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
      return prop;
	  }
	
	 /***
	  * @author mgp0966
	  * @info get property value from properties file
	  * @param propName
	  * @return
	  * @throws IOException
	  */
	 public String getProperty(String propName) throws IOException
	 {
		 Properties prop = readPropertiesFile(PROPFILENAME);
		 return prop.getProperty(propName);
	 }
	 
	 
	 /***
	  * @author mgp0966
	  * @info Base class that invokes the driver and set up execution sync timings, it also configures browser properties
	  * @return
	  * @throws IOException
	  */
	 
	@BeforeTest()
	 public WebDriver getDriver() throws IOException
	 {
		 if (getProperty("Browser").equalsIgnoreCase("chrome"))
		 {
			 //WebDriverManager.chromedriver().version("99.0.4844.51").setup();
			 //System.setProperty("webdriver.chrome.driver", USERDIR+"\\src\\test\\resources\\chromedriver.exe");
			 
			 WebDriverManager.chromedriver().setup();
			 
			 ChromeOptions options = new ChromeOptions();
			 options.addArguments("--disable-notifications");

			 if(getProperty("Headless").equalsIgnoreCase("true")) {
				 options.addArguments("--headless", "--window-size=1366,768");
				 driver = new ChromeDriver(options);
			 }else
			 {
				 driver = new ChromeDriver(options);
			 }
			 
		 }else if(getProperty("Browser").equalsIgnoreCase("firefox"))
		 {
			 WebDriverManager.firefoxdriver().setup();
			 driver = new FirefoxDriver();
		 }
		LOGGER.info("@@BeforeTest executed");
		
		Config config = new Config();
		driver.manage().timeouts().pageLoadTimeout(config.getPageLoadTimeOut(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(config.getImplicitWait(), TimeUnit.SECONDS);
		
		//set Extent report path
		htmlReporter = new ExtentHtmlReporter(USERDIR+"\\Reports\\ExecutionReport.html");
		htmlReporter.config().setDocumentTitle("SAP QA Team");
		htmlReporter.config().setReportName("Project Name - description");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		reporter = new ExtentReports();
		reporter.attachReporter(htmlReporter);
		reporter.setReportUsesManualConfiguration(true);
		
		return driver; 
	 }
	 
	public static ExtentTest getExtentReportObject(String testName)
	{
		//set Extent report path
		htmlReporter = new ExtentHtmlReporter(USERDIR+"\\Reports\\ExecutionReport.html");
		htmlReporter.config().setDocumentTitle("SAP QA Team");
		htmlReporter.config().setReportName("Project Name - description");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		reporter = new ExtentReports();
		reporter.attachReporter(htmlReporter);
		reporter.setReportUsesManualConfiguration(true);
		return reporter.createTest(testName);
	}
	 
	 /***
	  * @author mgp0966
	  * @info captures screenshot and returns string format  image path
	  * @param imageName
	  * @return
	  * @throws IOException
	  */
	 
	public String getScreenShot(String imageName) throws IOException{
		
		if(imageName.equals("")){
			imageName = "blank";
		}
		File image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String imagelocation = "./Reports/screenshots/";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualImageName = imagelocation+imageName+"_"+formater.format(calendar.getTime())+".png";
		File destFile = new File(actualImageName);
		FileHelper.copyFile(image, destFile);
		actualImageName = actualImageName.replace("./Reports/", "");
		return actualImageName;
	}
	 
	/***
	 * @author mgp0966	
	 * @info wait until the element is click enabled
	 * @param driver
	 * @param time
	 * @param element
	 * @return WebElement object
	 * @throws InterruptedException
	 */
	public WebElement waitForElementToBeClickable(WebDriver driver,long time,WebElement element) throws InterruptedException{
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, time);
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		catch(TimeoutException e) {
			
			LOGGER.catching(e);
		}
		return null;
	}
	
	/***
	 * @author mgp0966
	 * @info check for element visibility
	 * @param driver
	 * @param time
	 * @param element
	 * @return
	 */
	public WebElement waitForElementVisibility(WebDriver driver,long time,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, time);
		try {
			return wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch(TimeoutException e)
		{
			LOGGER.catching(e);
		}
		return null;
	}
	
	/***
	 * @author mgp0966
	 * @info check if element is hidden or invisible
	 * @param driver
	 * @param time
	 * @param element
	 * @throws InterruptedException
	 */
	public void waitForElementInvisibility(WebDriver driver,long time,WebElement element) throws InterruptedException{
		try {
			WebDriverWait wait = new WebDriverWait(driver, time);
			Thread.sleep(2000);
			wait.pollingEvery(Duration.ofSeconds(2));
			wait.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.attributeToBe(element, "visibility", "hidden"));
		}catch(TimeoutException e)
		{
			LOGGER.info(e.getMessage());
		}
	}
	
	/***
	 * @author mgp0966
	 * @info wait with polling interval
	 * @param driver
	 * @param time
	 * @param element
	 * @return
	 */
	public WebElement waitForElementWithPollingInterval(WebDriver driver,long time,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.pollingEvery(Duration.ofSeconds(2));
		wait.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
		
		
	/*	
		public void impliciteWait(long time) throws IOException{
			
			this.getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		}
	*/
/*		
		public void getresult(ITestResult result) throws IOException {
			if (result.getStatus() == ITestResult.SUCCESS) {

				test.log(LogStatus.PASS, result.getName() + " test is pass");
			} else if (result.getStatus() == ITestResult.SKIP) {
				test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
			} else if (result.getStatus() == ITestResult.FAILURE) {
				test.log(LogStatus.FAIL, result.getName() + " test is failed" + result.getThrowable());
				String screen = getScreenShot("");
				test.log(LogStatus.FAIL, test.addScreenCapture(screen));
			} else if (result.getStatus() == ITestResult.STARTED) {
				test.log(LogStatus.INFO, result.getName() + " test is started");
			}
		}
	*/	
		
	
	/***
	 * @author mgp0966
	 * @info AfterMethod code- logs a test case execution result
	 * @param result
	 * @throws IOException
	 */
	@AfterMethod()
	public void afterMethod(ITestResult result) throws IOException {
		LOGGER.info("@AfterMethod executed");

		if(result.getStatus()==ITestResult.FAILURE)
		{
			report.log(Status.FAIL, result.getThrowable().getMessage());
		}else if(result.getStatus()==ITestResult.SUCCESS)
		{
			report.log(Status.PASS, "Test Case Executed Successfully");
		}
		
		String imagePath = getScreenShot(result.getName());
		//ExtentTestManager.getTest().addScreenCaptureFromPath(imagePath);
		report.addScreenCaptureFromPath(imagePath);
		
	}
	
	/***
	 * @author mgp0966
	 * @info BeforeMethod - starts extent test session
	 * @param result
	 */
	@BeforeMethod()
	public void beforeMethod(Method result) {

		//ExtentManager.createInstance().attachReporter(reporter);
		//ExtentTestManager.startTest(result.getName());
		
		report = reporter.createTest(result.getName());
	
		report.log(Status.PASS, "Before Method Executed");
	}
	
	/***
	 * @author mgp0966
	 * @info AfterClass - end the extent test session
	 */
	@AfterClass(alwaysRun = true)
	public void endClass() {
		
		LOGGER.info("AfterClass executed");
		
		//driver.quit();		
		//ExtentTestManager.endTest();
		//reporter.flush();
	}
	
	/***
	 * @author mgp0966
	 * @info AfterSuite Method
	 */
	@AfterTest(alwaysRun = true)
	public void endTest() {
		
		LOGGER.info("AfterTest executed");
		
		driver.quit();		
		reporter.flush();
	}		
	
	/***
	 * @author mgp0966
	 * @param indexSI
	 * @param screenShotLocation
	 * @param response
	 * @throws IOException
	 */
	public static void updateResult(int indexSI,  String screenShotLocation,String response) throws IOException {
		String startDateTime = new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime());
	    System.out.println("startDateTime---"+startDateTime);

		//String resultFile = userDirector + "/src/main/java/com/sn/screenshot/TestReport.html";
		String resultFile = USERDIR + "Screenshots/TestReport.html";

		File file = new File(resultFile);
		System.out.println(file.exists());

		if (!file.exists()) {
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<html>" + "\n");
			bw.write("<head><title>" + "Test execution report" + "</title>" + "\n");
			bw.write("</head>" + "\n");
			bw.write("<body>");
			bw.write("<font face='Tahoma'size='2'>" + "\n");
			bw.write("<u><h1 align='center'>" + "Test execution report" + "</h1></u>" + "\n");
			bw.flush();
			bw.close();
		}
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(file, true));
		if (indexSI == 1) {

			bw1.write("<table align='center' border='0' width='70%' height='10'>");
			bw1.write("<tr><td width='70%' </td></tr>");
			bw1.write("<table align='center' border='1' width='70%' height='47'>");
			bw1.write("<tr>");
			bw1.write("<td colspan='1' align='center'><b><font color='#000000' face='Tahoma' size='2'>ScriptName :&nbsp;&nbsp;&nbsp;</font><font color='#0000FF'' face='Tahoma' size='2'>Resiliency Test </font></b></td>");
			bw1.write("<td colspan='2' align='left'><b><font color='#000000' face='Tahoma' size='2'>Start Time :&nbsp;</font></b><font color='#0000FF'' face='Tahoma' size='1'>" + startDateTime + " </font></td>");
			bw1.write("</tr>");
			bw1.write("</tr>");
			bw1.write("<td  bgcolor='#CCCCFF' align='center'><b><font color='#000000' face='Tahoma' size='2'>S.No</font></b></td>");
			bw1.write("<td  bgcolor='#CCCCFF' align='left'><b><font color='#000000' face='Tahoma' size='2'>Screen Shot</font></b></td>");
			bw1.write("<td  bgcolor='#CCCCFF' align='center'><b><font color='#000000' face='Tahoma' size='2'>Response </font></b></td>");
			bw1.write("</tr>");
		}

		bw1.write("<tr>" + "\n");
		bw1.write("<td bgcolor='#FFFFDC'align='Center'><font color='#000000' face='Tahoma' size='2'>" + indexSI + "</font></td>" + "\n");
		bw1.write("<td  bgcolor='#FFFFDC' valign='middle' align='left'><b><font color='#000000' face='Tahoma' size='2'>" + "<img src="+screenShotLocation+" alt='Smiley face' height='500' width='750'>" + "</font></b></td>" + "\n");
		bw1.write("<td  bgcolor='#FFFFDC' valign='middle' align='left'><b><font color='#000000' face='Tahoma' size='2'>" + response + "</font></b></td>" + "\n");
		bw1.write("</tr>" + "\n");
		bw1.write("</body>" + "\n");
		bw1.write("</html>");
		bw1.flush();
		bw1.close();
	}
	
	/***
	 * @author mgp0966
	 * @info javaScript executor to scroll through the view
	 * @param element
	 */
    public void jsScrollToView(WebElement element)
    {
    	 final JavascriptExecutor jse = (JavascriptExecutor) driver;
         jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }
		
	
}
