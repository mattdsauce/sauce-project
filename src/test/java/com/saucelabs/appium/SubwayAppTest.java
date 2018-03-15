package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SubwayAppTest {

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
        capabilities.setCapability("testobject_api_key", "DCAD109D67664430B2A35D611CF2D3CA"); //dias_p/r2-mobile
        capabilities.setCapability("testobject_device", "Motorola_Moto_G_3rd_gen_real");

        driver = new AndroidDriver(new URL("http://eu1.appium.testobject.com/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        resultWatcher.setRemoteWebDriver(driver);
    }



    @Test
    public void testMethod() throws Exception {

        WebElement el = driver.findElementById("signIn");
        el.click();

        try {
            el = driver.findElementByXPath("//android.widget.EditText[@resource-id='custom-signInName']");
        } catch (NoSuchElementException e) {
            driver.findElementById("com.android.chrome:id/terms_accept").click();
            driver.findElementById("com.android.chrome:id/negative_button").click();
            el = driver.findElementByXPath("//android.widget.EditText[@resource-id='custom-signInName']");
        }




        Thread.sleep(5000);

    }

}