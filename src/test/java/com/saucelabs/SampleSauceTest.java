package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.watcher.MattSauceTestWatcher;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class SampleSauceTest implements SauceOnDemandSessionIdProvider {

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
    public SampleSauceTest(String os, String version, String browser) {
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
        //capabilities.setCapability("screenResolution", "1920x1080");
        //capabilities.setCapability("extendedDebugging", true);
        //capabilities.setCapability("chromeOptions","{\"args\":[\"disable-infobars\"]}");
        //capabilities.setCapability("javascriptEnabled",true);
        //capabilities.setCapability("tunnelIdentifier", "matttunnel1");
        capabilities.setCapability("seleniumVersion", "3.14.0");
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
        capabilities.setCapability("name", "Sauce Sample Test: " + browser + " " + version + ", " + os);
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
        //System.out.println("Before driver.get: " + time_formatter.format(System.currentTimeMillis()));
        driver.get("http://saucelabs.com");
        //driver.get("http://www.google.com");
        //driver.get("http://localhost:8080/examples/servlets/servlet/HelloWorldExample");

        //System.out.println("After driver.get: " + time_formatter.format(System.currentTimeMillis()));

        //List<WebElement> anchors = driver.findElements(By.tagName("a"));
        //for (WebElement anchor: anchors) {
        //    System.out.println("anchor: " + anchor.getAttribute("outerHTML"));
        //}

        Thread.sleep(2000);

        //takeScreenshot("/Users/mattdunn/temp/scr1.png");

        // click a random link on the page
//        int r = randInt(1, anchors.size());
//        WebElement el = anchors.get(r);
//        if (browser.equalsIgnoreCase("safari")) {
//            el.click();
//        } else if (el.isDisplayed()) {
//            try {
//                el.click();
//            } catch (Exception e) {
//                //
//            }
//        }

        //anchors = driver.findElements(By.tagName("a"));

        /*WebElement el = driver.findElement(By.name("q"));
        el.clear();
        el.sendKeys("rabbits");
        el.submit();*/

        //WebElement el = driver.findElement(By.cssSelector("#mediaroomIcon"));

        /*WebElement el = driver.findElement(By.cssSelector("._2r81:nth-child(2) ._16Ez"));
        el.click();

        el = driver.findElement(By.cssSelector("._3RzH"));
        el.click();

        el = driver.findElement(By.cssSelector("._2cH1"));
        el.sendKeys("appium");
        el.submit();*/

        Thread.sleep(5000);

        //el = driver.findElement(By.id("frog"));

        //assertTrue(driver.getTitle().toLowerCase().contains("google"));
        assertTrue(driver.getTitle().toLowerCase().contains("sauce"));
        //assertEquals("MyPageTitle", driver.getTitle());
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }

    /**
     * Returns a psuedo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimim value
     * @param max Maximim value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
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
