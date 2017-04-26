package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class iOSTest implements SauceOnDemandSessionIdProvider {

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

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appiumVersion", "1.6.3");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "10.0");
        caps.setCapability("browserName", "safari");
        caps.setCapability("deviceName","iPhone Simulator");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("autoAcceptAlerts", true);
        caps.setCapability("javascript", true);
        //caps.setCapability("locationServicesEnabled", true);
        //caps.setCapability("nativeWebTap", true);
        caps.setCapability("name", "iOS Test");
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                caps);
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

        driver.get("https://www.amazon.com");

        //takeScreenshot("/Users/mattdunn/temp/scr1.jpg");
        //takeScreenshot("/Users/mattdunn/temp/scr2.jpg");
        //takeScreenshot("/Users/mattdunn/temp/scr3.jpg");
        //takeScreenshot("/Users/mattdunn/temp/scr4.jpg");
        //takeScreenshot("/Users/mattdunn/temp/scr5.jpg");
        //takeScreenshot("/Users/mattdunn/temp/scr6.jpg");
        //takeScreenshot("/Users/mattdunn/temp/scr7.jpg");
        //takeScreenshot("/Users/mattdunn/temp/scr8.jpg");
        //takeScreenshot("/Users/mattdunn/temp/scr9.jpg");
        //takeScreenshot("/Users/mattdunn/temp/scr10.jpg");

        Thread.sleep(10000);

        assertTrue(driver.getTitle().startsWith("Amazon"));

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
