package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class LocalAndroidBrowserTest {



    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;

    public LocalAndroidBrowserTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {


        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("browserName", "browser");
        caps.setCapability("platformVersion", "6.0");
        caps.setCapability("platformName","Android");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);

    }

    /**
     * Runs a simple test verifying the title of the google.com homepage.
     * @throws Exception
     */
    @Test
    public void loadPage() throws Exception {
        driver.get("http://localhost:8080/redirect.html");

        Thread.sleep(10000);

    }

    /**
     * Closes the {@link WebDriver} session.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


}
