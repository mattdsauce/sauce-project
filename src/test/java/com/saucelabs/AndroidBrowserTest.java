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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AndroidBrowserTest implements SauceOnDemandSessionIdProvider {

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

    public AndroidBrowserTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {

        String username = "harshashyama%40gmail.com";
        String accesskey = "c3060c17-d77f-429d-896c-e9e81161aaa1";

        DesiredCapabilities caps = DesiredCapabilities.android();
        //caps.setCapability("appiumVersion", "1.5.3");
        caps.setCapability("deviceName", "Android Emulator");
        //caps.setCapability("deviceType","phone");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("browserName", "Browser");
        caps.setCapability("platformVersion", "6.0");
        caps.setCapability("platformName","Android");
        caps.setCapability("name", "Android Browser Test");
        driver = new AndroidDriver<>(
                //new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                new URL("https://" + username + ":" + accesskey + "@ondemand.saucelabs.com/wd/hub"),
                caps);
        sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();




    }

    /**
     * Runs a simple test verifying the title of the google.com homepage.
     * @throws Exception
     */
    @Test
    public void loadPage() throws Exception {
        driver.get("https://google.com");

        // switch to NATIVE_APP context before taking screenshot
        // String current = ((AppiumDriver)driver).getContext();
        // ((AppiumDriver)driver).context("NATIVE_APP");

        // get screenshot
        // File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        // FileUtils.copyFile(screenshot, new File("/Users/mattdunn/temp/testScreenshot.png"));

        // switch back to previous context
        // ((AppiumDriver)driver).context(current);

        assertTrue(driver.getTitle().startsWith("Google"));
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