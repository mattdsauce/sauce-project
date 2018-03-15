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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class SageOneTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(System.getenv("SAUCE_USERNAME"), System.getenv("SAUCE_ACCESS_KEY"));

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
    public SageOneTest(String os, String version, String browser) {
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
        browsers.add(new String[]{"windows 8.1", "latest", "chrome"});
        browsers.add(new String[]{"windows 10", "latest", "chrome"});
        browsers.add(new String[]{"windows 8.1", "latest-1", "chrome"});
        browsers.add(new String[]{"windows 10", "latest-1", "chrome"});
        browsers.add(new String[]{"windows 8.1", "latest-2", "chrome"});
        browsers.add(new String[]{"windows 10", "latest-2", "chrome"});
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
        capabilities.setCapability("extendedDebugging", true);
        capabilities.setCapability("commandTimeout",90);
        //capabilities.setCapability("javascriptEnabled",true);
        //capabilities.setCapability("tunnelIdentifier", "matttunnel1");
        //capabilities.setCapability("chromedriverVersion", "2.35");
        capabilities.setCapability("name", "SageOne Test: " + browser + " " + version + ", " + os);
        capabilities.setCapability("build", "testBuild");
        this.driver = new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test
     * @throws Exception
     */
    @Test
    public void doTest() throws Exception {
        driver.get("https://app.sageone.com/login?locale=");

        WebElement el = driver.findElement(By.id("sso_Email"));
        el.sendKeys("sageoneselenium@gmail.com");

        el = driver.findElement(By.id("sso_Password"));
        el.sendKeys("Selenium@Sage1");

        el = driver.findElement(By.cssSelector("[type=\"submit\"]"));
        el.click();

        Thread.sleep(2000);


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
