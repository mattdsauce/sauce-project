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
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BPAndroidLocationTest {

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
        capabilities.setCapability("testobject_api_key", "AFCF3C44561B444E9E5589F6D9502A17"); // csteam/BPMeUK
        capabilities.setCapability("deviceName", "Samsung Galaxy S.*|Google.*|Huawei P20");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8");
        capabilities.setCapability("testobject_phone_only", true);
        capabilities.setCapability("testobject_appium_version", "1.8.1");
        capabilities.setCapability("appPackage","com.bp.mobile.bpme.uk");
        capabilities.setCapability("appActivity","com.bp.mobile.bpme.SplashScreenActivity");
        capabilities.setCapability("automationName","uiautomator2");
        capabilities.setCapability("testobject_test_name","P01_payWithVisaCardUK");
        driver = new AndroidDriver<>(new URL("https://eu1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


    @Test
    public void P01_payWithVisaCardUK() throws InterruptedException, IOException {

        WebElement el = waitForElementById("login_button");
        el.click();

        el = waitForElementById("login_web_view");

        el = waitForElementByXPath("//android.view.View[@resource-id=\"sfdc_username_container\"]//android.widget.EditText");
        el.sendKeys("bptestobject+15@gmail.com");

        el = waitForElementByXPath("//android.view.View[@resource-id=\"sfdc_password_container\"]//android.widget.EditText");
        el.sendKeys("Mir01Mar");

        el = waitForElementByXPath("//android.widget.Button[@text='Log in']");
        el.click();

        el = waitForElementById("title_text_view");
        Assert.assertEquals("Log In", el.getText());

        el = waitForElementById("enter_security_question_label");
        Assert.assertEquals("Security question", el.getText());

        el = waitForElementById("enter_security_question_spinner_layout");
        Assert.assertEquals("What was the name of your first pet?", el.getText());

        el = waitForElementById("answer_security_question_pin_edit_text");
        el.sendKeys("Rex");

        el = driver.findElementById("enter_mfa_done_button");
        el.click();

        el = waitForElementById("iamreadybutton");
        el.click();

        el = waitForElementById("com.android.packageinstaller:id/permission_allow_button");
        el.click();

        el = waitForElementById("toolbar_text_title");
        Assert.assertEquals("Pay for Fuel", el.getText());

        el = waitForElementByXPath("//android.widget.ImageButton[@content-desc='Navigate up']");
        el.click();

        el = waitForElementByXPath("//android.widget.CheckedTextView[@text='Account']");
        el.click();

        el = waitForElementById("toolbar_text_title");
        Assert.assertEquals("Account", el.getText());

        /*el = waitForElementByXPath("card_number_text_view");
        el.click();

        el = waitForElementById("credit_card_text_view");
        Assert.assertEquals("fuelCard2", el.getText());*/

        el = waitForElementByXPath("//android.widget.ImageButton[@content-desc='Navigate up']");
        el.click();

        el = waitForElementByXPath("//android.widget.CheckedTextView[@text='Pay for Fuel']");
        el.click();

        el = waitForElementById("toolbar_text_title");
        Assert.assertEquals("Pay for Fuel", el.getText());

        Location location = new Location(51.5044484, -0.105654, 0.0);
        driver.setLocation(location);

        el = waitForElementById("confirm_station_text_view");


        
        //takeScreenshot("/Users/mattdunn/temp/scr1.jpg");

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