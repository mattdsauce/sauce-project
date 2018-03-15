package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
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

import static org.junit.Assert.assertTrue;

public class iOSScrollTest implements SauceOnDemandSessionIdProvider {

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
    private AppiumDriver driver;


    @Before
    public void setUp() throws Exception {

        DesiredCapabilities caps = DesiredCapabilities.iphone();
        caps.setCapability("appiumVersion", "1.7.1");
        //caps.setCapability("deviceName","iPad Pro (12.9 inch) Simulator");
        caps.setCapability("deviceName","iPhone 7 Simulator");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("platformVersion","10.3");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "Safari");
        caps.setCapability("autoAcceptAlerts", true);
        caps.setCapability("javascript", true);
        caps.setCapability("name", "iOS Scroll Test");
        this.driver = new AppiumDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                caps);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
    }

    /**
     * Runs a simple test.
     * @throws Exception
     */
    @Test
    public void doTest() throws Exception {

        driver.get("https://www.amazon.com");

        TouchAction action = new TouchAction(driver);

        WebElement navbar = driver.findElement(By.id("navbar"));
        int startx = navbar.getLocation().getX();
        int starty = navbar.getLocation().getY();

        WebElement footer;
        try {
            // footer element on iPhone
            footer = driver.findElement(By.id("gwm-Nav-footer"));
        } catch (NoSuchElementException e) {
            // footer element on iPad
            footer = driver.findElement(By.id("rhf"));
        }
        int endx = footer.getLocation().getX();
        int endy = footer.getLocation().getY();

        action.press(startx+20,starty+20).moveTo(endx+20,endy+20).release().perform();

        Thread.sleep(10000);

        assertTrue(driver.getTitle().contains("Amazon"));

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
