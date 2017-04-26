package com.saucelabs.appium;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.text.MessageFormat;


public class AndroidInstagramTest implements SauceOnDemandSessionIdProvider {

    private AppiumDriver<WebElement> driver;

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
        String sauceUserName = authentication.getUsername();
        String sauceAccessKey = authentication.getAccessKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "5.1");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("deviceOrientation","portrait");
        capabilities.setCapability("appiumVersion", "1.5.3");
        capabilities.setCapability("name", "Android Instagram Test");
        capabilities.setCapability("app", "sauce-storage:instagram.apk");
        //capabilities.setCapability("appWaitActivity", "com.instagram.android.activity.MainTabActivity");
        capabilities.setCapability("javascriptEnabled", true);

        driver = new AndroidDriver<>(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUserName, sauceAccessKey)),
                capabilities);
        this.sessionId = driver.getSessionId().toString();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    @Test
    public void testApp() throws Exception {

        if (driver.findElements(By.xpath("//UIAAlert")).size() > 0) {
            // find alert, deal with it
            driver.findElement(By.xpath("//UIAButton[@name=\"OK\"]")).click();
        }

        if (driver.findElements(By.xpath("//UIAAlert")).size() == 0) {
            if (driver.findElement(By.xpath("//UIAStaticText[@name='Shopping']")) != null) {
                WebElement searchElem = driver.findElement(By.xpath("//UIATextField[@value='Search Stores']"));
                searchElem.clear();
                searchElem.sendKeys("~StoreCollect");
                driver.executeScript("UIATarget.localTarget().frontMostApp().keyboard().buttons()['Done'].tap();");
                driver.findElement(By.xpath("//UIATableCell[@name=\"~StoreCollect\"]")).click();
            }

        }


        Thread.sleep(15000);
    }

    public String getSessionId() {
        return sessionId;
    }
}