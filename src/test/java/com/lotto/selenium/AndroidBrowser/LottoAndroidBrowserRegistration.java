package com.lotto.selenium.AndroidBrowser;

import com.saucelabs.common.SauceOnDemandAuthentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;


import java.net.URL;
import java.util.LinkedList;

import static org.junit.Assert.*;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs
 * using multiple browsers in parallel.
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke
 * the Sauce REST API to mark the test as passed or failed.
 *
 * @author Venkatesham Varikuppala
 */
@RunWith(ConcurrentParameterized.class)
public class LottoAndroidBrowserRegistration implements SauceOnDemandSessionIdProvider {
	
	
//	public String username = System.getenv("SAUCE_USER_NAME") != null ? System.getenv("SAUCE_USER_NAME"): System.getenv("SAUCE_USERNAME");
//	public String accesskey = System.getenv("SAUCE_API_KEY") != null ? System.getenv("SAUCE_API_KEY"): System.getenv("SAUCE_ACCESS_KEY");
	
	public String username="venvarikuppala";	 
	public String accesskey="33c1fbce-0e3c-4c16-a502-f0f07a331483";

	/**
	 * Constructs a {@link SauceOnDemandAuthentication} instance using the
	 * supplied user name/access key. To use the authentication supplied by
	 * environment variables or from an external file, use the no-arg
	 * {@link SauceOnDemandAuthentication} constructor.
	 */
	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);

	/**
	 * JUnit Rule which will mark the Sauce Job as passed/failed when the test
	 * succeeds or fails.
	 */
	@Rule
	public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	@Rule
	public TestName name = new TestName() {
		public String getMethodName() {
			return String.format("%s : (%s %s %s)", super.getMethodName(), deviceName, platformVersion, platformName);
		};
	};
	/**
	 * Represents the AppiumVersion to be used as part of the test run.
	 */
	private String appiumVersion;
	/**
	 * Represents the deviceName to be used as part of the test run.
	 */
	private String deviceName;
	 	 /**
		 * Represents the deviceOrientation to be used as part of the test run.
		 */
	private String deviceOrientation;
	
	 /**
		 * Represents the deviceType to be used as part of the test run.
		 */
	private String deviceType;
	/**
	 * Represents the platformVersion to be used as part of the test run.
	 */
	private String platformVersion;	 
	
	/**
	 * Represents the platformName to be used as part of the test run.
	 */
	private String platformName;	 
	
	/**
	 * Represents the browserName to be used as part of the test run.
	 */
	private String browserName;	

	/**
	 * Represents the app to be used as part of the test run.
	 */
	//private String app;	
		 


	/**
	 * The {@link WebDriver} instance which is used to perform browser
	 * interactions with.
  */

	private String sessionId;

	/**
	 * The {@link WebDriver} instance which is used to perform browser
	 * interactions with.
	 */
	private AppiumDriver<WebElement> driver;

	/**
	 * Constructs a new instance of the test. The constructor requires three
	 * string parameters, which represent the operating system, version and
	 * browser to be used when launching a Sauce VM. The order of the parameters
	 * should be the same as that of the elements within the
	 * {@link #browsersStrings()} method.
	 * 
	 * @param appiumVersion
	 * @param deviceName
	 * @param deviceType
	 * @param deviceOrientation
	 * @param browserName
	 * @param platformVersion
	 * @param platformName
	 * //@param app
	 */
	
	public LottoAndroidBrowserRegistration(String appiumVersion, String deviceName, String deviceType, String deviceOrientation, String browserName, String platformVersion, String platformName) {
		//public LottoAndroidBrowserRegistration(String appiumVersion, String deviceName,String deviceType, String deviceOrientation,String browserName, String platformVersion, String platformName, String app) {
		super();
		this.appiumVersion = appiumVersion;
		this.deviceName = deviceName;
		this.deviceType=deviceType;
		this.deviceOrientation = deviceOrientation;
		this.browserName=browserName;
		this.platformVersion = platformVersion;
		this.platformName= platformName;
		//this.app= app;
	}


	/**
	 * @return a LinkedList containing String arrays representing the browser
	 *         combinations the test should be run against. The values in the
	 *         String array are used as part of the invocation of the test
	 *         constructor
	 */
	@ConcurrentParameterized.Parameters
	public static LinkedList browsersStrings() {
		LinkedList devices = new LinkedList();

		//String appZipName = "LottoNZ-SIT-ios.zip";
		
		
		//4.2 - api level 18  and 4.3 - api level -19 uses selendroid as automation framework, so we are not adding automation scripts for selendroid- so we ignored them for android emulator
//		// Android 4.2 versions		
//		// Android Emulator, 4.2 version
//		devices.add(new String[] { "1.5.0", "Android Emulator","phone","portrait","", "4.2","Android",  null });
//				
//		// Android Emulator, 4.3 version
//		devices.add(new String[] { "1.5.0", "Android Emulator","phone","portrait","", "4.3","Android",  null });
		
		
		
		// From 4.4 version - api level 20 uses android as automation
		//Android Emulator, 4.4 version
//		devices.add(new String[] { "1.5.1", "Android Emulator","phone","portrait","Browser", "4.4","Android",  null });
			
//		//Android Emulator, 5.0 version
//		devices.add(new String[] { "1.5.0", "Android Emulator","phone","portrait","", "5.0","Android",  null });
//		//Android Emulator, 5.1 version
		devices.add(new String[] { "1.5.1", "Android Emulator","phone","portrait","Browser", "5.1","Android" });
//
//		//LG Nexus 4 Emulator
//
//		//LG Nexus 4 Emulator, 4.2 version
//		devices.add(new String[] { "1.5.0", "LG Nexus 4 Emulator","phone","portrait","", "4.2","Android",  null });
//
//		//LG Nexus 4 Emulator, 4.3 version
//		devices.add(new String[] { "1.5.0", "LG Nexus 4 Emulator","phone","portrait","", "4.3","Android",  null });
//
//		//LG Nexus 4 Emulator, 4.4 version
//		devices.add(new String[] { "1.5.0", "LG Nexus 4 Emulator","phone","portrait","", "4.4","Android",  null });
//
//		//Google Nexus 7C Emulator
//
//		//Google Nexus 7C Emulator, 4.2 version
//		devices.add(new String[] { "1.5.0", "Google Nexus 7C Emulator","phone","portrait","", "4.2","Android",  null });
//		//Google Nexus 7C Emulator, 4.3 version
//		devices.add(new String[] { "1.5.0", "Google Nexus 7C Emulator","phone","portrait","", "4.3","Android",  null });
//
//		//Google Nexus 7C Emulator, 4.4 versionclear
		
//		devices.add(new String[] { "1.5.0", "Google Nexus 7C Emulator","phone","portrait","", "4.4","Android",  null });
//
//		// Samsung Galaxy Nexus Emulator
//		//Samsung Galaxy Nexus Emulator, 4.2 version
//		devices.add(new String[] { "1.5.0", "Samsung Galaxy Nexus Emulator","phone","portrait","", "4.2","Android",  null });
//		//Samsung Galaxy Nexus Emulator, 4.3 version
//		devices.add(new String[] { "1.5.0", "Samsung Galaxy Nexus Emulator","phone","portrait","", "4.3","Android",  null });
//
//		//Samsung Galaxy Nexus Emulator, 4.4 version
//		devices.add(new String[] { "1.5.0", "Samsung Galaxy Nexus Emulator","phone","portrait","", "4.4","Android",  null });
//
//		//Samsung Galaxy Tab 3 Emulator
//		//Samsung Galaxy Tab 3 Emulator, 4.2 version
//		devices.add(new String[] { "1.5.0", "Samsung Galaxy Tab 3 Emulator","phone","portrait","", "4.2","Android",  null });
//
//		//Samsung Galaxy S3 Emulator
//
//		//Samsung Galaxy S3 Emulator, 4.2 version
//		devices.add(new String[] { "1.5.0", "Samsung Galaxy S3 Emulator","phone","portrait","", "4.2","Android",  null });
//		//Samsung Galaxy S3 Emulator, 4.3 version
//		=devices.add(new String[] { "1.5.0", "Samsung Galaxy S3 Emulator","phone","portrait","", "4.3","Android",  null });
//
//		//Samsung Galaxy S3 Emulator, 4.4 version
//		devices.add(new String[] { "1.5.0", "Samsung Galaxy S3 Emulator","phone","portrait","", "4.4","Android",  null });
//
//		//Samsung Galaxy S4 Emulator
//
//		//Samsung Galaxy S4 Emulator, 4.2 version
//		devices.add(new String[] { "1.5.0", "Samsung Galaxy S4 Emulator","phone","portrait","", "4.2","Android",  null });
//		//Samsung Galaxy S4 Emulator, 4.3 version
//		devices.add(new String[] { "1.5.0", "Samsung Galaxy S4 Emulator","phone","portrait","", "4.3","Android",  null });
//
//		//Samsung Galaxy S4 Emulator, 4.4 version
//		devices.add(new String[] { "1.5.0", "Samsung Galaxy S4 Emulator","phone","portrait","", "4.4","Android",  null });
//
//		//Android GoogleAPI Emulator
//		//Android GoogleAPI Emulator, 4.4 version
//		devices.add(new String[] { "1.5.0", "Android GoogleAPI Emulator","phone","portrait","Browser", "4.4","Android",  null });
//		//Android GoogleAPI Emulator, 5.0 version
//		devices.add(new String[] { "1.5.0", "Android GoogleAPI Emulator","phone","portrait","Browser", "5.0","Android",  null });			
//	
		//Android Emulator Tablet, 4.4 version
//		devices.add(new String[] { "1.5.0", "Android Emulator","tablet","portrait","Browser", "4.4","Android",  null });			
//						
//Android Emulator Tablet, 5.0 version
//		devices.add(new String[] { "1.5.0", "Android Emulator","tablet","portrait","Browser", "5.0","Android",  null });			
//						
				
		//Android Emulator Tablet, 5.1 version
//		devices.add(new String[] { "1.5.0", "Android Emulator","tablet","portrait","Browser", "5.1","Android",  null });			
//							
								
								
		return devices;
	}

	/**
	 * @throws Exception
	 *             if an error occurs during the creation of the
	 *             {@link RemoteWebDriver} instance.
	 */
	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if ( appiumVersion!= null)
			capabilities.setCapability("appiumVersion", appiumVersion);
		if (deviceName != null)
			capabilities.setCapability("deviceName", deviceName);
		if (deviceOrientation != null)
			capabilities.setCapability("deviceOrientation", deviceOrientation);
		if (deviceType != null)
			capabilities.setCapability("deviceType", deviceType);
		if (platformVersion != null)
			capabilities.setCapability("platformVersion", platformVersion);
		if (platformName != null)
			capabilities.setCapability("platformName", platformName);
		if (browserName != null)
			capabilities.setCapability("browserName", browserName);
		//if (app != null)
		//	capabilities.setCapability("app", app);

		
		capabilities.setCapability("name", name.getMethodName());
		this.driver = new AndroidDriver<WebElement> (new URL("http://" + authentication.getUsername() + ":"+ authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), capabilities);		
		this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
		this.driver.get("https://1.sit.mylotto.co.nz/registration");
		
	}

	/**
	 * Runs a simple test verifying the UI and title of the belk.com home page.
	 * 
	 * @throws Exception
	 */
	@Test
    public void verifyregistrationPage() throws InterruptedException{
    	
		driver.get("https://1.sit.mylotto.co.nz/registration");
    	//sign up Texists
    	String SingUpText= driver.findElement(By.id("register-title")).getText();
    	assertTrue(SingUpText.equals("Sign up"));
    	
    	//provide Email address
    	int ran;
    	ran = 100 + (int)(Math.random() * ((10000 - 100) + 1));
    	driver.findElement(By.id("emailAddress")).sendKeys("signuptest" + ran + "@test.com");
    	// firstname
    	driver.findElement(By.id("firstName")).sendKeys("firstname");
    	//lastName
    	// lastname
    	driver.findElement(By.id("lastName")).sendKeys("lastname");
    	// password
    	
    	driver.findElement(By.name("password")).sendKeys("pass" + ran + "T_+@name");
    	
    	//dobDay
    	
    	driver.findElement(By.id("dobDay")).sendKeys("10");
    	//dobMonth
    	
    	Select mydrpdwn = new Select(driver.findElement(By.id("dobMonth")));
    	mydrpdwn.selectByValue("11");
    	
    	//Select listbox = new Select(driver.findElement(By.id("dobMonth")));
    	//listbox.selectByValue("12");
    	//dobYear
    	
    	driver.findElement(By.id("dobYear")).sendKeys("1990");
    	
    	//Results+Promotions
    	driver.findElement(By.id("promotionReminders")).isSelected();
    	//jackpot reminders+ Promotions
    	driver.findElement(By.name("jackpotReminder")).isSelected();
    	// Instant Kiwis and reminders
    	
    	driver.findElement(By.id("IKReminder")).isSelected();
    	
    	//sign up button 
    	
    	String Submitbutton=driver.findElement(By.id("signup-button")).getText();
    	//assertTrue(Submitbutton.equals("Sign up"));
    	driver.findElement(By.id("signup-button")).submit();
    	
    	
      
        
    }
    

	/**
	 * Closes the {@link WebDriver} session.
	 *
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	/**
	 *
	 * @return the value of the Sauce Job id.
	 */
	@Override
	public String getSessionId() {
		return sessionId;
	}
}

