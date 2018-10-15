package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class AndroidBrowserMRDCTest {

    /* Sets the test name to the name of the test method. */
    @Rule
    public TestName testName = new TestName();

    /* Takes care of sending the result of the tests over to TestObject. */
    @Rule
    public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability("testobject_api_key", "35984A06E3D747BA817DD3F5EB894A2E"); // csteam/amazon
        capabilities.setCapability("testobject_api_key", "2077678232ED4F1FA54D1A32D59BC091"); // albefree/google
        //capabilities.setCapability("testobject_api_key", "DB48D27EEC8E4CFDA9A8EB80E7127623"); // mattdsauce-sub2/saucelabs
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "9");
        driver = new AppiumDriver(new URL("http://us1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);

        /*Capabilities gotCaps = ((RemoteWebDriver) driver).getCapabilities();
        Map desired = (Map)gotCaps.asMap().get("desired");
        String device = (String)desired.get("deviceName");
        System.out.println("device = " + device);*/
    }


    /**
     * Runs a simple test
     * @throws Exception
     */
    @Test
    public void loadPage() throws Exception {
        driver.get("http://saucelabs.com");

        List<WebElement> anchors = driver.findElements(By.tagName("a"));

        //driver.get("http://www.bbc.co.uk/news/uk");

        Thread.sleep(20000);

        assertTrue(driver.getTitle().toLowerCase().contains("sauce"));
    }

}