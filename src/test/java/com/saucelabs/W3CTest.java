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
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;

import static junit.framework.TestCase.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class W3CTest implements SauceOnDemandSessionIdProvider {

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
    public W3CTest(String os, String version, String browser) {
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
        //browsers.add(new String[]{"Windows 10", "latest", "chrome"});
        //browsers.add(new String[]{"Windows 10", "11", "internet explorer"});
        //browsers.add(new String[]{"Windows 10", "17.17134", "MicrosoftEdge"});
        //browsers.add(new String[]{"Mac OS X 10.9", "60.0", "firefox"});
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
        capabilities.setCapability("browserName", browser);
        if (version != null) {
            capabilities.setCapability("browserVersion", version);
        }
        capabilities.setCapability("platformName", os);
        if (browser.equals("chrome")) {
            HashMap<String,Object> googOpts = new HashMap<String,Object>();
            googOpts.put("w3c",true);
            capabilities.setCapability("goog:chromeOptions", googOpts);
        } else if (browser.equals("internet explorer")) {
            capabilities.setCapability("iedriverVersion","3.11.0");
        }
        MutableCapabilities sauceCaps = new MutableCapabilities();
        sauceCaps.setCapability("seleniumVersion", "3.12.0");
        sauceCaps.setCapability("name", "W3C Test: " + browser + " " + version + ", " + os);
        sauceCaps.setCapability("username", authentication.getUsername());
        sauceCaps.setCapability("accessKey", authentication.getAccessKey());
        capabilities.setCapability("sauce:options", sauceCaps);

        this.driver = new RemoteWebDriver(new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"), capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test
     * @throws Exception
     */
    @Test
    public void loadpage() throws Exception {
        driver.get("https://google.com");

        WebElement el = driver.findElement(By.name("q"));

        Actions action = new Actions(driver);
        Action mouseOver = action.moveToElement(el).build();
        mouseOver.perform();

        el.clear();
        el.sendKeys("rabbits");
        el.submit();

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

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
