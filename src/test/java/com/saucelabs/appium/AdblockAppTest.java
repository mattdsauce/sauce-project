package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AdblockAppTest {

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
        capabilities.setCapability("testobject_api_key", "9FF630ACEC5A4255814631A7B30CE19B");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "8");
        capabilities.setCapability("phoneOnly", true);
        //capabilities.setCapability("testobject_appium_version", "1.6.4");
        driver = new IOSDriver(new URL("https://eu1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


    @Test
    public void doTest() throws InterruptedException, IOException {

        driver.getPageSource();
        takeScreenshot("/Users/mattdunn/temp/1.png");

        Thread.sleep(10000);
    }

}