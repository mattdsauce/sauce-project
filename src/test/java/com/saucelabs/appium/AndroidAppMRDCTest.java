package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AndroidAppMRDCTest {

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
        capabilities.setCapability("testobject_api_key", "366E6A2F39B94F12BB30742DE3571761"); //csteam/calculator2
        capabilities.setCapability("testobject_device", "Samsung_Galaxy_S6_POC115");
        //capabilities.setCapability("platformName", "Android");
        //capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("testobject_cache_device", true);
        //capabilities.setCapability("testobject_appium_version", "1.6.4");
        driver = new AndroidDriver<>(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


    @Test
    public void doTest() throws InterruptedException, IOException {

        /*WebElement el = driver.findElementByAccessibilityId("New note");
        el.click();

        el = driver.findElementByClassName("android.widget.EditText");
        el.sendKeys("This is a new note!");

        el = driver.findElementByAccessibilityId("Save");
        el.click();

        List<WebElement> els = driver.findElementsByClassName("android.widget.TextView");
        Assert.assertEquals(els.get(2).getText(), "This is a new note!");

        els.get(2).click();*/
        
        takeScreenshot("/Users/mattdunn/temp/scr1.jpg");

        Thread.sleep(10000);
    }

    @Test
    public void secondTest() throws InterruptedException {

        driver.getPageSource();
        Thread.sleep(5000);

    }

}