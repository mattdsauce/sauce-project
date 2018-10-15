package com.saucelabs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class iOSWebMRDCTest {

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
        capabilities.setCapability("testobject_api_key", "35984A06E3D747BA817DD3F5EB894A2E"); // csteam/amazon
        //capabilities.setCapability("testobject_api_key", "F870C979D57E4A79B3E3D8D40BF45FAC"); //appautosvc-testobj/sauce
        //capabilities.setCapability("deviceName", "iPhone_6S_Plus_11_applause");
        //capabilities.setCapability("testobject_appium_version", "1.8.1");
        //capabilities.setCapability("autoDismissAlerts", true);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "11");
        capabilities.setCapability("tabletOnly", true);
        //capabilities.setCapability("tunnelIdentifier", "mdtunnel");
        //capabilities.setCapability("browserName", "safari");
        //capabilities.setCapability("orientation", "PORTRAIT");
        //capabilities.setCapability("automationName", "XCUITest");
        driver = new IOSDriver(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);
        driver.manage().timeouts().pageLoadTimeout(130000, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().setScriptTimeout(130000, TimeUnit.MILLISECONDS);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    /**
     * Runs a simple test.
     * @throws Exception
     */
    @Test
    public void doTest() throws Exception {

        Thread.sleep(2000);

        driver.get("https://saucelabs.com");

        Thread.sleep(30000);
        assertTrue(driver.getTitle().toLowerCase().contains("sauce"));

    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


}
