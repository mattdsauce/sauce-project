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
        capabilities.setCapability("testobject_api_key", "09952D684DFC43E7AFC250CCFD879E70");
        capabilities.setCapability("testobject_device", "iPhone_7_32GB_10_real");
        capabilities.setCapability("automationName", "XCUITest");
        driver = new IOSDriver(new URL("http://appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    /**
     * Runs a simple test.
     * @throws Exception
     */
    @Test
    public void doTest() throws Exception {

        driver.get("https://www.amazon.com");

        WebElement el = driver.findElement(By.id("nav-search-keywords"));
        el.sendKeys("Trump hot sauce");
        el = driver.findElement(By.xpath("//*[@id=\"nav-search-form\"]/div[1]/div/input"));
        el.click();

        takeScreenshot("/Users/mattdunn/temp/scr1.jpg");

        el = driver.findElement(By.xpath("//*[@id=\"resultItems\"]/li[1]/div/div[1]/a/div[1]/img"));
        el.click();

        takeScreenshot("/Users/mattdunn/temp/scr2.jpg");

        Thread.sleep(10000);

        assertTrue(driver.getTitle().contains("HOT"));

    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


}
