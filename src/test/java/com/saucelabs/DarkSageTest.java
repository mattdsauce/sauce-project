package com.saucelabs;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.android.AndroidDriver;

public class DarkSageTest {

    public static final String USERNAME = System.getenv("SAUCE_USERNAME");
    public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
    public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
    public static final int WAITSECONDS = 60;
    public static final int MAXWAITSECONDS = 900; // Max time to wait for App to start

    public static WebDriver driver;
    // public static final String URL = "http://127.0.0.1:4723";

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability("platformName", "Android");

        // This section contains Android 4.4 versions
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability("deviceName", "Samsung Galaxy S4 Emulator");
        //capabilities.setCapability("deviceName","Samsung Galaxy S3 GoogleAPI Emulator");

        // This section contains Android 4.0 versions
        //capabilities.setCapability("platformVersion", "4.0");
        //capabilities.setCapability("deviceName", "Motorola Photon Q 4G Emulator");
        //capabilities.setCapability("automationName","Selendroid");

        capabilities.setCapability("appiumVersion", "1.5.3");

        capabilities.setCapability("name", "Appium 1.5.3 test");

        // Details on how to load the app (this was uploaded to the Sauce Storage server)
        capabilities.setCapability("app", "http://dw16.uptodown.com/dwn/pAHXAdu36CLAhm6oCcDWvufhkGIF9pqW7HB8eToWrCmd8qVgucavdi_8zDCB-87QYf8aAAsCY7EJrwx17j75d5xElMo1OCo711Gva8K1EqzMzAuyRBE4MLZSd3SglIbk/v4g-lOOPdnnaYRcb4dWtIRZBkNwgDUX2lSINM3V3NW9Rz2DGN3y9aboy3ZPnuuOuldE7NUVqRt_9ULRxDInWPSLMLuf2c2lzAyNwbeisdTEdqflfwYR51xwr-lI7e99c/LP2cst8_MJdpYFEcEbBId8vUbA9s-jElrOQLqUJ2U662-5R-CxznpYirhxpb0rgTFI9dFa224UqMttDiojB7Cl70fUxBX9ryCJLzHpou05wNDAF4OJL-hDi3J6ZxeaY2/3J3i-Q_CdFYSDty7NRCdUOdSlXfe6XGHOCY6D0d65-Q=/goal-com-8-5-1.apk");

        // Some devices appear to want appPackage, others demand app-package!
        capabilities.setCapability("appPackage", "com.freerange360.mpp.GOAL");
        capabilities.setCapability("app-package", "com.freerange360.mpp.GOAL");
        capabilities.setCapability("appActivity", "perform.goal.android.ui.splash.SplashActivity");
        capabilities.setCapability("app-activity", "perform.goal.android.ui.splash.SplashActivity");
        // Browsername is set to blank to indicate an App
        capabilities.setCapability("browserName", "");

        //capabilities.setCapability("deviceOrientation", "portrait");

        // AVD Emulated devices are incorrect by default (EST-4 timezone set and Pacific assumed!)
        capabilities.setCapability("timeZone", "London");
        driver = new AndroidDriver<WebElement>(new URL(URL), capabilities);
    }

    @Test
    public void testActions() throws Exception {

        waitForVisible(new By.ById("com.freerange360.mpp.GOAL:id/message_text"), MAXWAITSECONDS);
        driver.findElement(By.id("com.freerange360.mpp.GOAL:id/confirm_button")).click();
        waitForVisible(new By.ById("com.freerange360.mpp.GOAL:id/toolbar"), WAITSECONDS);
        driver.findElement(By.name("transfer zone tab")).click();
        waitForVisible(new By.ById("com.freerange360.mpp.GOAL:id/transfer_zone_list_refresh_container"), WAITSECONDS);
        driver.findElement(By.name("explore tab")).click();
        waitForVisible(new By.ById("com.freerange360.mpp.GOAL:id/autocomplete_search_bar"), WAITSECONDS);
        driver.findElement(By.name("matches tab")).click();
        waitForVisible(new By.ById("com.freerange360.mpp.GOAL:id/expanded_calendar"), WAITSECONDS);
        driver.findElement(By.name("settings tab")).click();

        // If needed can ask Sauce to break allowing for manual testing
        //((JavascriptExecutor)driver).executeScript("sauce: break");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private static void waitForVisible(final By by, int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }
}