package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
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

public class iOSWebSauceTest implements SauceOnDemandSessionIdProvider {

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
        caps.setCapability("appiumVersion", "1.6.5");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "9.3");
        caps.setCapability("browserName", "safari");
        caps.setCapability("deviceName","iPhone 6 Simulator");
        caps.setCapability("deviceOrientation", "landscape");
        caps.setCapability("autoAcceptAlerts", true);
        caps.setCapability("javascript", true);
        caps.setCapability("name", "iOS Web Sauce Test");
        this.driver = new RemoteWebDriver(
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

        WebElement el = driver.findElement(By.id("nav-search-keywords"));
        el.sendKeys("Trump hot sauce");
        el = driver.findElement(By.xpath("//*[@id=\"nav-search-form\"]/div[1]/div/input"));
        el.click();

        takeScreenshot("/Users/mattdunn/temp/scr1.jpg");

        el = driver.findElement(By.xpath("//*[@id=\"resultItems\"]/li[1]/div/div[1]/a/div[1]/img"));
        el.click();

        takeScreenshot("/Users/mattdunn/temp/scr2.jpg");

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
