package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class BarneysTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(System.getenv("SAUCE_USERNAME"), System.getenv("SAUCE_ACCESS_KEY"));
    //public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("mdunn77sub1", "e52b6e4c-2a01-4df1-a8aa-640a9c4fb4cd");

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);
    //public MattSauceTestWatcher resultReportingTestWatcher = new MattSauceTestWatcher(this, authentication);

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

    private SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Constructs a new instance of the test.  The constructor requires three string parameters, which represent the operating
     * system, version and browser to be used when launching a Sauce VM.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     * @param os
     * @param version
     * @param browser
     */
    public BarneysTest(String os, String version, String browser) {
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
        //browsers.add(new String[]{"OS X 10.11", "beta", "chrome"});
        //browsers.add(new String[]{"Windows 10", "11", "internet explorer"});
        //browsers.add(new String[]{"Windows 10", "15", "MicrosoftEdge"});
        //browsers.add(new String[]{"Windows 7", "latest", "chrome"});
        //browsers.add(new String[]{"windows 7", "9", "internet explorer"});
        //browsers.add(new String[]{"windows 7", "10", "internet explorer"});
        //browsers.add(new String[]{"windows 8.1", "56", "chrome"});
        //browsers.add(new String[]{"windows 10", "latest", "chrome"});
        //browsers.add(new String[]{"Windows 10", "dev", "firefox"});
        //browsers.add(new String[]{"Windows 10", "beta", "firefox"});
        //browsers.add(new String[]{"Windows 10", "latest", "firefox"});
        //browsers.add(new String[]{"OS X 10.11", "10.0", "safari"});
        //browsers.add(new String[]{"Windows 10", "latest", "chrome"});
        //browsers.add(new String[]{"OS X 10.11", "10.0", "safari"});
        //browsers.add(new String[]{"macOS 10.12", "10.1", "safari"});
        browsers.add(new String[]{"macOS 10.13", "latest", "chrome"});
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
        //capabilities.setCapability("screenResolution", "1400x1050"); // 2048x1536
        //capabilities.setCapability("extendedDebugging", true);
        //capabilities.setCapability("chromeOptions","{\"args\":[\"disable-infobars\"]}");
        capabilities.setCapability("javascriptEnabled",true);
        //capabilities.setCapability("tunnelIdentifier", "matttunnel1");
        //capabilities.setCapability("seleniumVersion", "3.9.1");
        //capabilities.setCapability("iedriverVersion", "3.4.0");
        //capabilities.setCapability("chromedriverVersion", "2.35");
        //capabilities.setCapability("captureHtml",true);
        //capabilities.setCapability("marionette", true);
        capabilities.setCapability("avoidProxy", true);
        //capabilities.setCapability("unexpectedAlertBehaviour", "ignore");
        //capabilities.setCapability("timeZone", "London");
        //capabilities.setCapability("public", "private");
        //capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability("name", "Barneys Test: " + browser + " " + version + ", " + os);
        //capabilities.setCapability("build", "testBuild");
        //System.out.println("Before creating RemoteWebDriver: " + time_formatter.format(System.currentTimeMillis()));

        /*ChromeOptions opts = new ChromeOptions();
        opts.addArguments("start-maximized", "disable-infobars", "test-type");
        capabilities.setCapability(ChromeOptions.CAPABILITY, opts);*/

        this.driver = new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                //new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@localhost:4445/wd/hub"),
                capabilities);
        //System.out.println("After creating RemoteWebDriver: " + time_formatter.format(System.currentTimeMillis()));
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test
     * @throws Exception
     */
    @Test
    public void loadpage() throws Exception {

        driver.get("https://website:green@staging.barneys.com");

        Thread.sleep(2000);

        WebElement el = driver.findElement(By.xpath("//div[@id='top-nav']/ul[@id='topnav-level-1']/li/a[contains(text(),'Men')]"));
        //WebElement el = driver.findElement(By.cssSelector(".atg_store_dropDownParent:nth-child(6) .accord-header"));
        Actions act = new Actions(driver);
        act.moveToElement(el).build().perform();
        //act.moveToElement(el).build().perform();
        //act.moveByOffset(1, 0).build().perform();


        Thread.sleep(5000);

        assertTrue(driver.getTitle().toLowerCase().contains("barneys"));

    }



    /**
     * Closes the {@link WebDriver} session.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        //System.out.println("Before driver.quit: " + time_formatter.format(System.currentTimeMillis()));
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
