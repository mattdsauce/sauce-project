package com.saucelabs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class NJMTest {

    /* Sets the test name to the name of the test method. */
    @Rule
    public TestName testName = new TestName();

    /* Takes care of sending the result of the tests over to TestObject. */
    @Rule
    public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private AppiumDriver<WebElement> driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", "35984A06E3D747BA817DD3F5EB894A2E"); // glauture/quoteandbuyho4
        //capabilities.setCapability("platformName", "Android");
        //capabilities.setCapability("platformVersion", "7.0");
        //capabilities.setCapability("privateDevicesOnly", true);
        capabilities.setCapability("testobject_device", "Samsung Galaxy S7");
        //capabilities.setCapability("tunnelIdentifier", "testTunnel");
        capabilities.setCapability("name", "Test with Sauce Connect");
        //capabilities.setCapability("browserName", "chrome");
        driver = new AndroidDriver<>(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);

        Capabilities gotCaps = ((RemoteWebDriver) driver).getCapabilities();
        Map desired = (Map)gotCaps.asMap().get("desired");
        String device = (String)desired.get("deviceName");
        System.out.println("device = " + device);
    }


    @Test
    public void doTest() throws Exception {

        driver.get("https://google.com");
        Thread.sleep(5000);

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

    }

}