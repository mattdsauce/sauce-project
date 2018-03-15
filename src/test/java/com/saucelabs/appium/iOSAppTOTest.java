package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class iOSAppTOTest{

    private AppiumDriver<WebElement> driver;

    /* Takes care of sending the result of the tests over to TestObject. */
    @Rule
    public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

    /**
     * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
     * SAUCE_USERNAME and SAUCE_ACCESS_KEY environment variables to reference your Sauce account details.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", "A6595D46DB1C4698914FBDE5E575C6E5"); // csteam/calculator
        capabilities.setCapability("deviceName", "iPhone_SE_10_2_POC108");
        //capabilities.setCapability("platformName", "iOS");
        //capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("noReset", false);
        //capabilities.setCapability("testobject_cache_device", true);
        capabilities.setCapability("automationName", "XCUITest");
        //capabilities.setCapability("deviceOrientation", "landscape");

        driver = new IOSDriver<WebElement>(new URL("http://us1.appium.testobject.com/wd/hub"), capabilities);
        //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        resultWatcher.setRemoteWebDriver(driver);
    }

    //@After
    //public void tearDown() throws Exception {
    //    driver.quit();
    //}


    @Test
    public void testMethod() throws Exception {

        String elemText = driver.findElementsByClassName("UIAStaticText").get(0).getText();
        assertNotNull(elemText);

    }

    @Test
    public void testMethod2() throws Exception {

        String elemText = driver.findElementsByClassName("UIAStaticText").get(0).getText();
        assertNotNull(elemText);

    }

}