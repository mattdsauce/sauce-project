package com.saucelabs;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class iOSMRDCSCTest {

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
        //capabilities.setCapability("testobject_api_key", "4BC9B5A340BB456A96C238CDA92283BC"); // gamesys-scp-pov/google
        //capabilities.setCapability("testobject_device", "iPhone_SE_10_2_POC101");
        //capabilities.setCapability("testobject_appium_version", "1.6.5");
        capabilities.setCapability("autoDismissAlerts", true);
        capabilities.setCapability("platformName", "iOS");
        //capabilities.setCapability("deviceName", "iPhone SE");
        capabilities.setCapability("platformVersion", "10");
        //capabilities.setCapability("privateDevicesOnly", true);
        capabilities.setCapability("tunnelIdentifier", "mdtunnel1");
        capabilities.setCapability("name", "Test with Sauce Connect");
        //capabilities.setCapability("browserName", "safari");
        //capabilities.setCapability("deviceOrientation", "portrait");
        capabilities.setCapability("automationName", "XCUITest");
        driver = new AppiumDriver(new URL("https://eu1.appium.testobject.com/wd/hub"), capabilities);
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

        driver.get("https://google.com");
        //Thread.sleep(5000);

        //driver.get("http://localhost:8080/examples/");
        Thread.sleep(5000);

        assertTrue(driver.getTitle().contains("Google"));

    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


}
