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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.LinkedList;

@RunWith(ConcurrentParameterized.class)
public class Bhargava45765Test implements SauceOnDemandSessionIdProvider {

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
    public Bhargava45765Test(String os, String version, String browser) {
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
        //browsers.add(new String[]{"Windows 7", "11", "internet explorer"});
        browsers.add(new String[]{"macOS 10.12", "48", "firefox"});
        //browsers.add(new String[]{"Windows 8", "latest", "chrome"});
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
        capabilities.setCapability("screenResolution", "1024x768");
        capabilities.setCapability("seleniumVersion", "3.5.0");
        capabilities.setCapability("name", "Bhargava 45765 Test: " + browser + " " + version + ", " + os);
        this.driver = new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                //new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@localhost:4445/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test
     * @throws Exception
     */
    @Test
    public void doTest() throws Exception {

        WebElement element;

        /*driver.get("https://ccqa48auto0924c--ccrzqard.na73.visual.force.com/apex/ccrzqard__homepage?portalUser=0051I000001AormQAC&store=DefaultStore");
        Thread.sleep(5000);
        element = driver.findElement(By.id("username"));
        element.sendKeys("admin@ccqa48auto0924c.test");
        element = driver.findElement(By.id("password"));
        element.sendKeys("cloudcraze1qa");
        element = driver.findElement(By.id("Login"));
        element.click();
        Thread.sleep(10000);
        element = driver.findElement(By.xpath("//div[contains(@class,'container')]//a[contains(text(),'Products')]"));
        element.click();
        Thread.sleep(3000);
        element = driver.findElement(By.xpath("//a[contains(text(),'Products')]/following::ul//li/a[contains(text(),'Product Sub Menu')]"));
        new Actions(driver).moveToElement(element).build().perform();

        Thread.sleep(5000);*/

        /*driver.get("https://www.eureqatest.com/training/");
        Thread.sleep(5000);
        element = driver.findElement(By.linkText("JavaScript Alerts"));
        element.click();
        element = driver.findElement(By.cssSelector("button.btn-custom"));
        element.click();
        //Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();*/

        driver.get("http://the-internet.herokuapp.com/javascript_alerts");
        driver.findElements(By.cssSelector("button")).get(1).click();
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        Thread.sleep(5000);

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
