package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class YahooSportsTest implements SauceOnDemandSessionIdProvider {

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
     * Represents the browser to be used as part of the test run.
     */
    private String browser;
    /**
     * Represents the operating system to be used as part of the test run.
     */
    private String os;
    /**
     * Represents the version of the browser to be used as part of the test run.
     */
    private String version;
    /**
     * Instance variable which contains the Sauce Job Id.
     */
    private String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;

    /**
     * Constructs a new instance of the test.  The constructor requires three string parameters, which represent the operating
     * system, version and browser to be used when launching a Sauce VM.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     * @param os
     * @param version
     * @param browser
     */
    public YahooSportsTest(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        //browsers.add(new String[]{"OS X 10.10", "47", "firefox"});
        browsers.add(new String[]{"OS X 10.10", "8.0", "safari"});
        return browsers;
    }


    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the {@link #browser},
     * {@link #version} and {@link #os} instance variables, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver} instance.
     */
    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) {
            capabilities.setCapability(CapabilityType.VERSION, version);
        }
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        //capabilities.setCapability("javascriptEnabled",true);
        //capabilities.setCapability("tunnelIdentifier", "mattpool");
        //capabilities.setCapability("seleniumVersion", "2.53.1");
        //capabilities.setCapability("captureHtml",true);
        //capabilities.setCapability("marionette", false);
        //capabilities.setCapability("avoidProxy", true);
        //capabilities.setCapability("screenResolution", "1280x1024");
        //capabilities.setCapability("timeZone", "London");
        capabilities.setCapability("name", "Yahoo Sports Test: " + browser + " " + version + ", " + os);
        //capabilities.setCapability("build", "testBuild");
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                //new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@localhost:4445/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void loadpage() throws Exception {
        driver.get("http://sports.yahoo.com/nhl/teams/san/");

        WebElement el = driver.findElement(By.cssSelector(".ys-player-header"));
        assertTrue(el.isEnabled());

        el = driver.findElement(By.cssSelector(".ys-division-standings"));
        assertTrue(el.isEnabled());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,800)");

        el = driver.findElement(By.cssSelector(".ys-team-schedule"));
        assertTrue(el.isEnabled());

        js.executeScript("window.scrollTo(0,0)");

        el = driver.findElement(By.cssSelector(".ys-team-key-stats"));
        assertTrue(el.isEnabled());

        el = driver.findElement(By.cssSelector(".ys-team .sub-nav"));
        assertTrue(el.isEnabled());

        List<WebElement> els = driver.findElements(By.cssSelector(".ys-team .sub-nav ul li"));

        el = driver.findElement(By.cssSelector(".tdv2-applet-stream"));
        assertTrue(el.isEnabled());

        el = driver.findElement(By.cssSelector(".tdv2-applet-featurebar"));
        assertTrue(el.isEnabled());

        js.executeScript("window.scrollBy(0,160)");

        el = driver.findElement(By.cssSelector("a[class*=\"tab-orange\"]"));
        assertTrue(el.getText().equals("News"));

        el = driver.findElement(By.cssSelector(".ys-team .sub-nav ul li[data-tst=\"Schedule\"] a[href*=\"teams\"]"));
        el.click();

        els = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".ys-team .latest-results-table")));


        Thread.sleep(20000);

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
