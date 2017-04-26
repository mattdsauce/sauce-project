package com.saucelabs;

import java.net.URL;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class HoneywellTest implements SauceOnDemandSessionIdProvider {

	private AppiumDriver<MobileElement> driver;

	private String sessionId;

	/**
	 * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
	 * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
	 */
	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

	/**
	 * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
	 */
	public @Rule
	SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);


	/**
	 * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
	 * SAUCE_USERNAME and SAUCE_ACCESS_KEY environment variables to reference your Sauce account details.
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		String appPackageName = "com.honeywell.android.lyric.qa";

		String sauceUserName = authentication.getUsername();
		String sauceAccessKey = authentication.getAccessKey();
		DesiredCapabilities capabilities = DesiredCapabilities.android();
		capabilities.setCapability("automationName", "appium");
		capabilities.setCapability("name", "LoginTest");
		capabilities.setCapability("autoLaunch", false);
		capabilities.setCapability("appActivity", "com.honeywell.android.arena.controller.splashscreen.SplashScreenActivity");
		capabilities.setCapability("appWaitActivity", "com.honeywell.android.arena.controller.login.LoginActivity,com.honeywell.android.arena.controller.location.HomeActivity");
		capabilities.setCapability("appWaitPackage", appPackageName);
		capabilities.setCapability("appPackage", appPackageName);
		capabilities.setCapability("deviceOrientation", "portrait");
		capabilities.setCapability("appiumVersion", "1.4.16");
		capabilities.setCapability("deviceName", "Android GoogleAPI Emulator");
		capabilities.setCapability("app", "sauce-storage:" + "lyricgeneralQa.zip");
		capabilities.setCapability("browserName", "");
		capabilities.setCapability("platformVersion", "5.0");

		driver = new AndroidDriver<MobileElement>(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUserName, sauceAccessKey)),
				capabilities);
		this.sessionId = driver.getSessionId().toString();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}


	@Test
	public void testApp() throws Exception {

        if (driver != null) {

            System.out.println("Launching Application ...");
            // Launching the App
            driver.launchApp();

            // Setting the User ID
            setValueToElement("ID", "fragment_login_user_name", "Austinprod@grr.la");

            // Setting the Password
            setValueToElement("ID", "fragment_login_password", "Password123");

            clickOnElement("ID", "fragment_login_login_button");

            try {

                // Checking for the Home page element
                getMobileElement("ID", "actionbar_activity_log_image");

                // Clicking on the Hamburger Icon
                clickOnElement("XPATH", "//*[@class='android.widget.ImageButton']");

                // Scrolling to make Logout option visible
                MobileElement element = (MobileElement) driver.findElementsByLinkText("Logout");

                // Clicking on logout
                element.click();

            } catch (NoSuchElementException e) {
                System.out.println("Login failed");
            }


        } else {
            System.out.println("Not able to instantiate Driver, Skipping test.");
        }
    }
	
	
	
	
	public MobileElement getMobileElement(String strategyType,String value) throws NoSuchElementException{
		
		System.out.println("Finding element " + value + " ...");
		
		if(driver==null){
			throw new NoSuchElementException("Driver is null");
		}else{
			FluentWait<AppiumDriver<MobileElement>> fWait = new FluentWait<AppiumDriver<MobileElement>>(driver);
			
			fWait.withTimeout(30, TimeUnit.SECONDS);
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.ignoring(NoSuchElementException.class);
			
			By by = null;
			
			switch(strategyType.toUpperCase()){
			case "XPATH":
				by = By.xpath(value);
				break;
			case "ID":
				by = By.id(value);
				break;
			default:
				by = null;
			}
			
			try{
				return (by!=null)?(MobileElement)fWait.until(ExpectedConditions.presenceOfElementLocated(by)):null;
			}catch(TimeoutException e){
				e.printStackTrace();
				throw new NoSuchElementException("Element got timed. Error Message - " + e.getMessage());
			}catch(WebDriverException e){
				throw new NoSuchElementException("Got WebDriver Exception. Error Message - " + e.getMessage());
			}catch(Exception e){
				throw new NoSuchElementException("Got UnExpected Exception. Error Message - " + e.getMessage());
			}
		}
	}
	
	
	
	public boolean setValueToElement(String strategyType,String locatorValue,String valueToSet){
		try{
			
			MobileElement element = getMobileElement(strategyType, locatorValue);
			
			if(element!=null){
				System.out.println("Setting value to element - " + locatorValue + " ....");
				element.sendKeys(valueToSet);
			}
			
		}catch(NoSuchElementException e){
			System.out.println(e.getMessage());
			return false;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	
	
	public boolean clickOnElement(String strategyType,String locatorValue){
		try{

			MobileElement element = getMobileElement(strategyType, locatorValue);
			
			if(element!=null){
				System.out.println("Doing click on element - " + locatorValue + " ....");
				element.click();
			}
			
		}catch(NoSuchElementException e){
			System.out.println(e.getMessage());
			return false;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

    public String getSessionId() {
        return sessionId;
    }
	
	
}
