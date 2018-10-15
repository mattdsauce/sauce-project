package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.html5.Location;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.URL;

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
        capabilities.setCapability("testobject_api_key", "6D8A00C532BE4A00922E0A7CCEE833D3"); // csteam/calculator-1
        //capabilities.setCapability("testobject_api_key", "8E501FA913594D1581218F57F75F2C7A"); // appautosvc-testobj/getyourguide-
        //capabilities.setCapability("testobject_api_key", "68C38DEB749A48E6B5F9A05495CC45EB"); // mattdsauce-sub2/calculator
        //capabilities.setCapability("testobject_api_key", "869775F4543E428280A99D817804F398"); // arcadiacosta/tlproplussit
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "11");
        capabilities.setCapability("tabletOnly",true);
        capabilities.setCapability("automationName","XCUITest");
        //capabilities.setCapability("deviceName", " iPhone_7_11_4_real");

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

        //Location location = new Location(53.798655, -1.5, 0);
        //driver.setLocation(location);

        //Location thelocation = driver.location();


        String elemText = driver.findElementsByClassName("UIAStaticText").get(0).getText();
        assertNotNull(elemText);

        Thread.sleep(10000);

    }

    /*@Test
    public void testMethod2() throws Exception {

        String elemText = driver.findElementsByClassName("UIAStaticText").get(0).getText();
        assertNotNull(elemText);

    }*/

}