package com.saucelabs.appium;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectAppiumSuite;
import org.testobject.appium.junit.TestObjectAppiumSuiteWatcher;
import org.testobject.rest.api.appium.common.TestObject;

import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/* You must add these two annotations on top of your test class. */
@TestObject(testLocally = false, testObjectApiKey = "04922ECB126C4C9C96EFD69DBB19A137", testObjectSuiteId = 7)
@RunWith(TestObjectAppiumSuite.class)
public class ArztDataTest {

    private AppiumDriver driver;

    @Rule
    public TestName testName = new TestName();

    @Rule
    public TestObjectAppiumSuiteWatcher resultWatcher = new TestObjectAppiumSuiteWatcher();

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("testobject_api_key", resultWatcher.getApiKey());
        capabilities.setCapability("testobject_test_report_id", resultWatcher.getTestReportId());

        driver = new AndroidDriver(new URL("https://eu1.appium.testobject.com/wd/hub"), capabilities);

        resultWatcher.setRemoteWebDriver(driver);

    }

    @Test
    public void testMethod() {
        /* Your test. */
    }


}