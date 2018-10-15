package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
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
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MyAndroidLocationTest {

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
        capabilities.setCapability("testobject_api_key", "32AB2AADFD7340D2AB6B18C85EEE0772"); // csteam
        capabilities.setCapability("deviceName", "Samsung Galaxy S.*|Google.*[^C]|Huawei P20");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8");
        capabilities.setCapability("testobject_phone_only", true);
        capabilities.setCapability("testobject_appium_version", "1.8.1");
        capabilities.setCapability("automationName","uiautomator2");
        capabilities.setCapability("testobject_test_name","Matt Android Location Test");
        driver = new AndroidDriver<>(new URL("https://eu1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


    @Test
    public void MyLocationTest() throws InterruptedException, IOException {

        //driver.getPageSource();

        WebElement el = driver.findElementByXPath("//*[@text='Indoor']");
        WebElement eltop = driver.findElementByXPath("//*[@text='Basic Map']");

        TouchAction action = new TouchAction(driver);
        action.longPress(el).moveTo(eltop).release().perform();
        action.perform();

        el = waitForElementByXPath("//*[@text='My Location Demo']");
        el.click();

        driver.switchTo().alert().accept();

        el = driver.findElementByAccessibilityId("My Location");
        el.click();

        Thread.sleep(2000);

        Location location = new Location(51.5044484, -0.105654, 0.0);
        driver.setLocation(location);

        Thread.sleep(5000);

        el = driver.findElementByAccessibilityId("My Location");
        el.click();

        Thread.sleep(5000);
    }


    public WebElement waitForElementById(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
        return driver.findElementById(locator);
    }

    public WebElement waitForElementByXPath(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        return driver.findElementByXPath(locator);
    }


}