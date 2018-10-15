package com.saucelabs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
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

public class GamesysIOSWebTest {

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
        capabilities.setCapability("testobject_api_key", "3786992BA12D4DA78BBC46678AF9B99C"); // gamesys/amazon
        capabilities.setCapability("deviceName", "iPhone.*");
        //capabilities.setCapability("testobject_appium_version", "1.8.1");
        capabilities.setCapability("autoDismissAlerts", true);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "11");
        //capabilities.setCapability("tunnelIdentifier", "mdtunnel");
        //capabilities.setCapability("browserName", "safari");
        //capabilities.setCapability("orientation", "PORTRAIT");
        //capabilities.setCapability("automationName", "XCUITest");
        driver = new IOSDriver(new URL("https://gamesys.testobject.com/wd/hub"), capabilities);
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

        driver.get("https://google.com");

        Thread.sleep(5000);

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


}
