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

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class SunBasketTest implements SauceOnDemandSessionIdProvider {

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
    public SunBasketTest(String os, String version, String browser) {
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
        //capabilities.setCapability("extendedDebugging", true);
        capabilities.setCapability("chromedriverVersion", "2.35");
        capabilities.setCapability("name", "Sun Basket Test: " + browser + " " + version + ", " + os);
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

        driver.get("https://master.sunbasket-staging.com/?offer=QA-TEST35OFF");

        WebElement el = driver.findElement(By.xpath("//a[@href='/join'][text()='Redeem Offer']"));
        el.click();

        el = driver.findElement(By.xpath("//*[@id='firstName']"));
        el.sendKeys("sb0216201874410445");

        el = driver.findElement(By.xpath("//*[@id='lastName']"));
        el.sendKeys("sb");

        el = driver.findElement(By.xpath("//*[@id='email']"));
        el.sendKeys("sb0216201874410445@testsbemail.com");

        el = driver.findElement(By.xpath("//*[@id='password']"));
        el.sendKeys("ReplacePassword123");

        el = driver.findElement(By.xpath("//input[@id='zip'][@placeholder='Zip code']"));
        el.sendKeys("94588");

        el = driver.findElement(By.xpath("//form[@id='userJoinForm']/fieldset//button[1]"));
        el.click();

        el = driver.findElement(By.xpath("//input[@id='autocomplete']"));
        el.sendKeys("Ownes Dr");

        el = driver.findElement(By.xpath("//*[@name='address2']"));
        el.sendKeys("925");

        el = driver.findElement(By.xpath("//*[@id='phone']"));
        el.sendKeys("9259259259");

        el = driver.findElement(By.xpath("//*[@id='setup-form']/fieldset/div[4]/div[10]/input"));
        el.sendKeys("Door Delivery");

        el = driver.findElement(By.xpath("//form/fieldset//button[@id='submit-button']"));
        el.click();

        el = driver.findElement(By.xpath("//img[@src='/resources/img/logo.svg']"));
        el.click();

        el = driver.findElement(By.xpath("//div[@class='offer-message semibold center ']/a[1]"));
        el.getText();

        el = driver.findElement(By.xpath("//*[@id='settings-dropdown']"));
        el.click();

        el = driver.findElement(By.xpath("//*[@class='dropdown open']//a[@href='/logout']"));
        el.click();

        el = driver.findElement(By.xpath("//*[@id='nav-logged-out']//li[@class='hidden-xs']/a[@class='logout-link nav-btn']"));
        el.click();


        assertTrue(driver.getTitle().toLowerCase().contains("sun basket"));
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
