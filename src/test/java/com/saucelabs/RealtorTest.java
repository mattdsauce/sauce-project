package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import static org.junit.Assert.assertTrue;

public class RealtorTest implements SauceOnDemandSessionIdProvider {

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

    public RealtorTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("appiumVersion", "1.5.3");
        caps.setCapability("deviceName", "Samsung Galaxy S6 Device");
        //caps.setCapability("deviceType","phone");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("platformVersion", "6.0");
        caps.setCapability("platformName","Android");
        caps.setCapability("locationServicesAuthorized", true);
        caps.setCapability("autoAcceptAlerts", true);
        caps.setCapability("name", "Realtor Test");
        driver = new AndroidDriver<>(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                caps);
        sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();




    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void loadPage() throws Exception {
        driver.get("http://beta.realtor.com");

        // switch to NATIVE_APP context to accept alert if present
        //String current = ((AppiumDriver)driver).getContext();
        //((AppiumDriver)driver).context("NATIVE_APP");

        //WebDriverWait wait = new WebDriverWait(driver, 60);
        //wait.until(ExpectedConditions.alertIsPresent());
        //Alert alert = driver.switchTo().alert();
        //alert.accept();

        // switch back to previous context
        //((AppiumDriver)driver).context(current);

        Thread.sleep(20000);

        assertTrue(driver.getTitle().startsWith("Find Real Estate"));
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
