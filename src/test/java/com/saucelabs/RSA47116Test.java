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
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class RSA47116Test implements SauceOnDemandSessionIdProvider {

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
    public RSA47116Test(String os, String version, String browser) {
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
        browsers.add(new String[]{"Windows 10", "11", "internet explorer"});
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
        MutableCapabilities sauceCaps = new MutableCapabilities();
        sauceCaps.setCapability("seleniumVersion", "3.11.0");
        sauceCaps.setCapability("name", "RSA 47116 Test: " + browser + " " + version + ", " + os);
        sauceCaps.setCapability("screenResolution","1920x1080");
        sauceCaps.setCapability("iedriverVersion", "3.11.0");
        capabilities.setCapability("sauce:options", sauceCaps);
        //capabilities.setCapability("screenResolution","1920x1080");
        //capabilities.setCapability("seleniumVersion", "3.8.0");
        //capabilities.setCapability("iedriverVersion", "3.8.0");
        //capabilities.setCapability("name", "RSA 47116 Test: " + browser + " " + version + ", " + os);
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
    public void tc001_switchBrowserTesting() throws Exception {

        driver.get("https://www.w3schools.com/html/html_links.asp");
        Thread.sleep(3000);
        String parentWindow = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//*[@id='main']/div[5]/a")));
        Thread.sleep(1000);
        js.executeScript("arguments[0].click();",driver.findElement(By.xpath("//*[@id='main']/div[5]/a")));
        //driver.findElement(By.xpath("//*[@id='main']/div[5]/a")).click();
        switchBrowser("Tryit Editor v3.5");
        System.out.println("Title of the new browser window [" + driver.getTitle() + "].");
        driver.switchTo().window(parentWindow);
        dismissExactBrowser("Tryit Editor v3.5");

    }

    /**

     * Dismiss Exact browser
     * @param title
     * @throws NoSuchWindowException
     */
    public void dismissExactBrowser(String title) throws Exception {

        String parentWindowId = driver.getWindowHandle();
        //Get Handles of all the open Popup Windows, iterate through set and check if title matches any...
        Set<String> allWindows = driver.getWindowHandles();
        if (!allWindows.isEmpty()) {
            for (String windowId : allWindows) {
                try {
                    if (driver.switchTo().window(windowId).getTitle().equals(title)) {
                        driver.close();
                        Thread.sleep(1000);
                        break;
                    }
                }
                catch (NoSuchWindowException e) {
                    throw e;
                }
            }
        }

        //Move back to the Parent Browser Window
        driver.switchTo().window(parentWindowId);
    }

    /**
     * switch browser method.
     * @param title
     * @throws Exception
     */
    public void switchBrowser(String title) throws Exception {

        boolean found = false;
        //Get Handles of all the open Popup Windows, iterate through set and check if title matches any...
        Set<String> allWindows = driver.getWindowHandles();
        if (!allWindows.isEmpty()) {
            for (String windowId : allWindows) {
                if (driver.switchTo().window(windowId).getTitle().contains(title)) {
                    found = true;
                    driver.switchTo().window(windowId);
                    break;
                }
            }
        }
        if (found == false) {
            throw new Exception("ERROR: The window = " + title + " was Not found!");
        }
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
