package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.URL;

public class KaufiOSAppTOTest {

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
        capabilities.setCapability("testobject_api_key", "86EB9F82BA674035BE7FBE8820D1D378"); // csteam/kaufda
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "11");
        //capabilities.setCapability("deviceName", "iPhone 6S");
        capabilities.setCapability("phoneOnly", true);
        capabilities.setCapability("appiumVersion", "1.8.0");
        capabilities.setCapability("automationName", "XCUITest");
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

        WebElement el = driver.findElementByAccessibilityId("KDSegmentedViewController_Search_Button");
        el.click();

        el = driver.findElementByAccessibilityId("KDSearchController_SearchBar");
        el.sendKeys("Brot");

        el = driver.findElementByAccessibilityId("KDSearchController_SearchBar");
        el.sendKeys("");

        el = driver.findElementByXPath("(//XCUIElementTypeButton[@name=\"KDBrochureCollectionViewCell_BrochureButton\"])[2]");
        el.click();

        new TouchAction(driver)
                .press(355,363)
                .moveTo(0,363)
                .release()
                .perform();

        Thread.sleep(5000);

        //driver.getPageSource();

    }


}