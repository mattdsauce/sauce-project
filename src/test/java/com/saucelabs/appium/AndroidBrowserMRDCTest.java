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
    private AppiumDriver<WebElement> driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", "35984A06E3D747BA817DD3F5EB894A2E"); // csteam/amazon
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "7.1");
        capabilities.setCapability("testobject_appium_version", "1.7.1");
        driver = new AndroidDriver<>(new URL("http://eu1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);

        Capabilities gotCaps = ((RemoteWebDriver) driver).getCapabilities();
        Map desired = (Map)gotCaps.asMap().get("desired");
        String device = (String)desired.get("deviceName");
        System.out.println("device = " + device);
    }


    /**
     * Runs a simple test
     * @throws Exception
     */
    @Test
    public void loadPage() throws Exception {
        driver.get("http://www.bbc.co.uk/news");

        //List<WebElement> anchors = driver.findElements(By.tagName("a"));

        driver.get("http://www.bbc.co.uk/news/uk");

        Thread.sleep(2000);

        assertTrue(driver.getTitle().toLowerCase().contains("bbc"));
    }

}