package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectAppiumSuite;
import org.testobject.appium.junit.TestObjectAppiumSuiteWatcher;
import org.testobject.rest.api.appium.common.TestObject;

import java.io.IOException;
import java.net.URL;

/* You must add these two annotations on top of your test class. */
@TestObject(testLocally = false, testObjectApiKey = "CEEB1FFBBCE74B3FB59FFB916805F12B", testObjectSuiteId = 7) // neerusqa/crcl/appium/suites
@RunWith(TestObjectAppiumSuite.class)
public class CRCLSuiteTest {

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
        System.out.println("report ID: " + resultWatcher.getTestReportId());
        driver = new AndroidDriver<>(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }


    @Test
    public void doTest() throws InterruptedException, IOException {

        Thread.sleep(10000);

        driver.findElement(By.id("com.crcl.android:id/emailEdt")).sendKeys("lee@gmail.com");
        driver.findElement(By.id("com.crcl.android:id/passwordEdt")).sendKeys("123456");
        driver.findElement(By.id("com.crcl.android:id/loginBtn")).click();

        Thread.sleep(10000);
    }

}