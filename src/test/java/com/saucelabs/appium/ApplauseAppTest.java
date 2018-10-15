package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class ApplauseAppTest {

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

    //private String applause_private_api_key = "84577786251A45EF979862FD56F68BC7"; // appautosvc-ms/mmde-ios
    //private String applause_private_api_key = "0FFE8AB71BEC4797865ECB714CEA9D77"; // appautosvc-ms/mmdesede-prod
    //private String applause_private_api_key = "9DABFA82C0A64484A2F9A93A158D73D1"; // appautosvc-ms/mediamarktde
    //private String applause_private_api_key = "6AAFD54D2F05422D8D91C09970670E59"; // appautosvc-disney/disneylife-ios
    private String applause_private_api_key = "A8437F1AC73B44259EC0225E1A5E312E"; // appautosvc-testobj/getyourguidenew

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", applause_private_api_key);
        capabilities.setCapability("deviceName", "iPhone_6S_Plus_Applause");
        //capabilities.setCapability("platformName", "iOS");
        //capabilities.setCapability("platformVersion", "11");
        driver = new AppiumDriver<WebElement>(new URL("https://eu1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }

    @Test
    public void doTest() throws InterruptedException, IOException {

        Set<String> contexts = driver.getContextHandles();
        System.out.println("Contexts: " + contexts);

        Thread.sleep(10000);
    }

}