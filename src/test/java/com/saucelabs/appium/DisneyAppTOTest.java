package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
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
import java.util.List;

public class DisneyAppTOTest {

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
        capabilities.setCapability("testobject_api_key", "40CC192F86CA4ED8A4E17FC57DFEB1F8");
        //capabilities.setCapability("testobject_device", "Samsung_Galaxy_Note_3_real");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "5.1");
        capabilities.setCapability("appPackage", "com.disney.mdx.wdw.google");
        capabilities.setCapability("appActivity", "com.disney.wdpro.android.mdx.activities.LoaderActivity");
        capabilities.setCapability("appWaitActivity", "com.disney.wdpro.park.activities.TutorialSecondLevelActivity, com.disney.wdpro.park.activities.FinderActivity");
        //capabilities.setCapability("testobject_cache_device", true);
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

        WebElement el = driver.findElementById("com.disney.mdx.wdw.google:id/pulldown_image");

        takeScreenshot("/Users/mattdunn/temp/scr1.jpg");

        Thread.sleep(10000);
    }


}