package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class iosPopupTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(System.getenv("SAUCE_USERNAME"),
            System.getenv("SAUCE_ACCESS_KEY"));

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * Instance variable which contains the Sauce Job Id.
     */
    private String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;


    @Before
    public void setUp() throws Exception {

        DesiredCapabilities caps = DesiredCapabilities.iphone();
        caps.setCapability("appiumVersion", "1.6.4");
        caps.setCapability("commandTimeout", "300");
        caps.setCapability("safariIgnoreFraudWarning", "true");
        caps.setCapability("idleTimeout", "360");
        caps.setCapability("name", "iOS Popup Test");
        caps.setCapability("safariAllowPopups", "true");
        caps.setCapability("autoAcceptAlerts", "true");
        caps.setCapability("deviceName", "iPhone Simulator");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("automationName", "XCUITest");
        caps.setCapability("platformVersion", "10.2");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "Safari");
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), caps);
        //driver = new IOSDriver<WebElement>(new URL("http://localhost:4723/wd/hub"), caps);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        this.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

    }

    /**
     * Runs a simple test.
     * @throws Exception
     */
    @Test
    public void loadPage() throws Exception {

        System.out.println("opening page");
        driver.get("https://www.cleanandclear.com/blotting-papers/clean-clear-oil-absorbing-sheets");
        System.out.println("page opened");

        System.out.println("before click button to show social links");
        driver.findElement(By.xpath(".//*[@id='janrain-social-share-text-button']")).click();
        System.out.println("clicked button to show social links");

        System.out.println("before click button to open facebook log in");
        WebElement el = driver.findElement(By.xpath(".//button[contains(@class,'facebookButton')]"));
        assertTrue(el.isEnabled());
        el.click();
        System.out.println("clicked button to open facebook log in");

        Thread.sleep(10000);

        assertTrue(driver.getTitle().contains("CLEAN"));

    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
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

    /**
     *
     * @return the value of the Sauce Job id.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }
}
