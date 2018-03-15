package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.URL;

public class BPiOSAppTOTest {

    private AppiumDriver driver;

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
        capabilities.setCapability("testobject_api_key", "1C15A8A20CB6432AA924759F4B1D0CAA");
        capabilities.setCapability("testobject_device", "iPhone_5_16GB_real");
        //capabilities.setCapability("testobject_device", "iPhone_7_32GB_10_real");
        //capabilities.setCapability("testobject_appium_version", "1.6.4");
        //capabilities.setCapability("automationName", "XCUITest");
        //capabilities.setCapability("deviceOrientation", "landscape");

        driver = new IOSDriver(new URL("http://eu1.appium.testobject.com/wd/hub"), capabilities);
        //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        resultWatcher.setRemoteWebDriver(driver);
    }

    //@After
    //public void tearDown() throws Exception {
    //    driver.quit();
    //}


    @Test
    public void testMethod() throws Exception {

        Thread.sleep(5000);

    }

}