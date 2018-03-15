package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.URL;

import static org.junit.Assert.assertNotNull;

public class SmarpIOSTOTest {

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
        //capabilities.setCapability("testobject_api_key", "EEFC335C67D540E3A7CF7EA6879F5280"); // csteam/smarpios
        capabilities.setCapability("testobject_api_key", "CEE49A86977940F89B26B7B8765D8A25"); // appautosvc-testobj/smarpios
        capabilities.setCapability("testobject_device", "iPhone_6S_Plus_32GB_Applause");
        //capabilities.setCapability("platformName", "iOS");
        //capabilities.setCapability("platformVersion", "10");
        //capabilities.setCapability("phoneOnly", true);
        //capabilities.setCapability("appiumVersion", "1.6.5");
        capabilities.setCapability("automationName", "XCUITest");
        //capabilities.setCapability("deviceOrientation", "landscape");

        driver = new IOSDriver<WebElement>(new URL("http://eu1.appium.testobject.com/wd/hub"), capabilities);
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

}