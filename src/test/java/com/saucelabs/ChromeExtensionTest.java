package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;

public class ChromeExtensionTest implements SauceOnDemandSessionIdProvider {

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

    public ChromeExtensionTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {


        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("./Adblock-Plus_v1.9.3.crx"));
        options.addArguments("start-maximized", "disable-webgl", "blacklist-webgl", "blacklist-accelerated-compositing",
                "disable-accelerated-2d-canvas", "disable-accelerated-compositing", "disable-accelerated-layers",
                "disable-accelerated-plugins", "disable-accelerated-video", "disable-accelerated-video-decode",
                "disable-gpu", "disable-infobars", "test-type");
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        caps.setCapability("platform", "Windows 8.1");
        caps.setCapability("version", "latest");
        caps.setCapability("name", "Chrome Extension Test");

        // logging stuff
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                caps);
        sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

        // find Adblock Plus 'first run' tab, close it and switch focus back to first tab
        String subWindowHandler = null;
        String firstTab = null;
        Set<String> handles = this.driver.getWindowHandles(); // get all windows
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()) {
            subWindowHandler = iterator.next();
            driver.switchTo().window(subWindowHandler);
            if (driver.getTitle().contains("Adblock")) {
                driver.close();
            } else if (driver.getTitle().contains("data:")) {
                firstTab = subWindowHandler;
            }
        }
        //driver.switchTo().window(firstTab);


    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void amazon() throws Exception {
        driver.get("http://www.amazon.com/");
        analyzeLog();
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }

    public void analyzeLog() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            //do something useful with the data
        }
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
