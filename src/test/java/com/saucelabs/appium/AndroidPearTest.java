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
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AndroidPearTest implements SauceOnDemandSessionIdProvider {

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
        //capabilities.setCapability("platformName", "Android");
        //capabilities.setCapability("platformVersion", "5.1");
        //capabilities.setCapability("deviceName", "Android Emulator");
        //capabilities.setCapability("deviceType", "phone");
        //capabilities.setCapability("deviceOrientation","portrait");
        //capabilities.setCapability("appiumVersion", "1.5.3");
        //capabilities.setCapability("name", "Android Pear App Test");
        //capabilities.setCapability("app", "sauce-storage:reSET.apk");
        capabilities.setCapability("javascriptEnabled", true);

        //driver = new AndroidDriver<>(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUserName, sauceAccessKey)), capabilities);
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
        this.sessionId = driver.getSessionId().toString();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    @Test
    public void appTest() throws InterruptedException {

        WebElement el = driver.findElementByAccessibilityId("startButton");
        el.click();

        List<WebElement> els = driver.findElementsByAccessibilityId("descriptionText");
        assertEquals("Select a category to browse and begin a treatment module", els.get(0).getText());

        el = driver.findElementByAccessibilityId("category_Treatment");
        assertTrue(el.isDisplayed());

        for(String context : driver.getContextHandles()){
            System.out.println("available context handle: " + context);
        }
        System.out.println("current context: " + driver.getContext());

        el = driver.findElementByAccessibilityId("category_Mood Matters");
        assertTrue(el.isDisplayed());

        el = driver.findElementByAccessibilityId("category_Life Skills");
        assertTrue(el.isDisplayed());

        el = driver.findElementByAccessibilityId("category_Sex Health");
        assertTrue(el.isDisplayed());

        el = driver.findElementByAccessibilityId("category_Hep C and HIV");
        assertTrue(el.isDisplayed());


        Thread.sleep(15000);
    }

    public String getSessionId() {
        return sessionId;
    }
}