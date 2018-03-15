package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectAppiumSuite;
import org.testobject.appium.junit.TestObjectAppiumSuiteWatcher;
import org.testobject.rest.api.appium.common.TestObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/* You must add these two annotations on top of your test class. */
@TestObject(testLocally = false, testObjectApiKey = "699F1F0D6C2D49688B6B7AE49C5603CD", testObjectSuiteId = 7) // using navitasqa's api key
@RunWith(TestObjectAppiumSuite.class)
public class BPAndroidSuiteTest {

    /* Sets the test name to the name of the test method. */
    @Rule
    public TestName testName = new TestName();

    /* Takes care of sending the result of the tests over to TestObject. */
    @Rule
    public TestObjectAppiumSuiteWatcher resultWatcher = new TestObjectAppiumSuiteWatcher();

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private AppiumDriver<WebElement> driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", resultWatcher.getApiKey());
        capabilities.setCapability("testobject_test_report_id", resultWatcher.getTestReportId());
        capabilities.setCapability("appPackage", "com.bp.mobile.bpme.uk");
        capabilities.setCapability("appActivity", "com.bp.mobile.bpme.SplashScreenActivity");
        System.out.println("report ID: " + resultWatcher.getTestReportId());
        driver = new AndroidDriver<>(new URL("http://eu1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


    @Test
    public void doTest() throws InterruptedException, IOException {

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        WebElement el;

        try {
            el = driver.findElementById("com.bp.mobile.bpme.uk:id/iamreadybutton");
            el.click();

            el = driver.findElementById("com.android.packageinstaller:id/permission_allow_button");
            el.click();
        } catch (Exception e) {
            // in case the permission button doesn't show up
        }

        el = driver.findElementById("com.bp.mobile.bpme.uk:id/toolbar_text_title");
        assertEquals("Pay for Fuel", el.getText());

        el = driver.findElementByXPath("//android.widget.ImageButton[@content-desc='Navigate up']");
        el.click();


        takeScreenshot("/Users/mattdunn/temp/scr1.jpg");

        Thread.sleep(10000);
    }

}