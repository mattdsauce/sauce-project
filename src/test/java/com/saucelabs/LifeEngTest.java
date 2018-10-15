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
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class LifeEngTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(System.getenv("SAUCE_USERNAME"), System.getenv("SAUCE_ACCESS_KEY"));
    //public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("mattd-sub5a", "c43bfbd4-4423-4dc5-b025-c705521d72a2");

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
    public LifeEngTest(String os, String version, String browser) {
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
        //browsers.add(new String[]{"Windows 7", "11", "internet explorer"});
        //browsers.add(new String[]{"macOS 10.12", "latest", "chrome"});
        browsers.add(new String[]{"macOS 10.13", "latest", "safari"});
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
        capabilities.setCapability("screenResolution", "1920x1440");
        //capabilities.setCapability("extendedDebugging", true);
        //capabilities.setCapability("chromeOptions","{\"args\":[\"disable-infobars\"]}");
        //capabilities.setCapability("javascriptEnabled",true);
        //capabilities.setCapability("tunnelIdentifier", "matttunnel1");
        //capabilities.setCapability("seleniumVersion", "3.12.0");
        //capabilities.setCapability("iedriverVersion", "3.12.0");
        //capabilities.setCapability("chromedriverVersion", "2.35");
        //capabilities.setCapability("captureHtml",true);
        //capabilities.setCapability("marionette", true);
        //capabilities.setCapability("avoidProxy", true);
        //capabilities.setCapability("unexpectedAlertBehaviour", "ignore");
        //capabilities.setCapability("timeZone", "Chicago");
        //capabilities.setCapability("public", "private");
        //capabilities.setCapability("prerun","sauce-storage:sauce_file_seed.sh");
        //capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        //capabilities.setCapability("_recordMp4",true);
        capabilities.setCapability("name", "LifeEng Test: " + browser + " " + version + ", " + os);
        //capabilities.setCapability("build", "testBuild");
        //System.out.println("Before creating RemoteWebDriver: " + time_formatter.format(System.currentTimeMillis()));
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
        driver.get("http://reachclient.oint.mr.tv3cloud.com/?oauth=test");

        WebElement el = waitForElementByCss("input[type='submit'][value='Log in']");
        assertTrue(el.isDisplayed());

        el = driver.findElement(By.cssSelector("[name='UserName']"));
        assertTrue(el.isDisplayed());
        el.sendKeys("duplexscaleuser5806@tv3test.com");

        el = driver.findElement(By.cssSelector("[name='Password']"));
        assertTrue(el.isDisplayed());
        el.sendKeys("Esoteric$");

        el = driver.findElement(By.cssSelector("input[type='submit'][value='Log in']"));
        el.click();

        el = waitForElementByCss("#screen");

        el = waitForElementByCss("#mediaroomIcon");

        Thread.sleep(5000);

    }

    public WebElement waitForElementByCss(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
        return driver.findElement(By.cssSelector(locator));
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
