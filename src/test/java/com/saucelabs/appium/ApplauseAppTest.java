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

    private String applause_private_api_key = "C5EE825B61254454A27D4D76A9E2CEE9";

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", applause_private_api_key);
        //capabilities.setCapability("testobject_device", "Samsung_Galaxy_S6_Edge_applause_us");
        capabilities.setCapability("testobject_device", "Samsung_Galaxy_S6_real");
        capabilities.setCapability("testobject_appium_version", "1.6.4");
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

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']")));

        WebElement el = driver.findElementByXPath("//android.widget.LinearLayout[@resource-id='com.android.packageinstaller:id/dialog_container']//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']");
        el.click();

        Set<String> contexts = driver.getContextHandles();
        System.out.println("Contexts: " + contexts);

        Thread.sleep(10000);
    }

}