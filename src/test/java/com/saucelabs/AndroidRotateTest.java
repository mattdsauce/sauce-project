package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class AndroidRotateTest implements SauceOnDemandSessionIdProvider {

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

        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("appiumVersion", "1.4.16");
        caps.setCapability("deviceName","Android Emulator");
        caps.setCapability("deviceType","phone");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("browserName", "Browser");
        caps.setCapability("platformVersion", "5.1");
        caps.setCapability("platformName","Android");
        caps.setCapability("rotatable", true);
        caps.setCapability("name", "Android Rotate Test");
        this.driver = new AndroidDriver<>(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                caps);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
        //this.driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);

    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void loadPage() throws Exception {

        driver.get("http://www.saucelabs.com");
        Assert.assertEquals(driver.getTitle(), "Sauce Labs: Selenium Testing, Mobile Testing, JS Unit Testing");

        ((AppiumDriver) driver).rotate(ScreenOrientation.LANDSCAPE);

        Thread.sleep(10);

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
