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

public class ClareControlsTest {

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
        capabilities.setCapability("testobject_api_key", "B04254CA102B42CE9F32DFE290F47D2E"); // testobject_cc/ch-beta
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "10.2");
        capabilities.setCapability("appiumVersion", "1.6.4");
        capabilities.setCapability("automationName", "XCUITest");

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

        driver.removeApp("com.clarecontrols.beta.ClareControls");
        driver.launchApp();

        elemText = driver.findElementsByClassName("UIAStaticText").get(0).getText();
        assertNotNull(elemText);

    }

}