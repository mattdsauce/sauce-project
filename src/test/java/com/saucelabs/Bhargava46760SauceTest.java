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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@RunWith(ConcurrentParameterized.class)
public class Bhargava46760SauceTest implements SauceOnDemandSessionIdProvider {

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
     * Represents the device to be used as part of the test run.
     */
    private String device;
    /**
     * Represents the iOS version to be used as part of the test run.
     */
    private String iosVersion;
    /**
     * Instance variable which contains the Sauce Job Id.
     */
    private String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;

    /**
     * Constructs a new instance of the test.  The constructor requires two string parameters, which represent the device
     * and iOS version to be used when launching a Sauce simulator.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     * @param device
     * @param iosVersion
     */
    public Bhargava46760SauceTest(String device, String iosVersion) {
        super();
        this.device = device;
        this.iosVersion = iosVersion;
    }

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        browsers.add(new String[]{"iPhone Simulator", "11.1"});
        browsers.add(new String[]{"iPhone Simulator", "10.3"});
        return browsers;
    }


    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined
     *
     * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver} instance.
     */
    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", device);
        capabilities.setCapability("platformVersion", iosVersion);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("appiumVersion", "1.7.1");
        capabilities.setCapability("name", "Bhargava 46760 Test: " + device + " iOS " + iosVersion);
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

        driver.manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);

        System.out.println("Open Website");
        driver.get("https://www.staples.com");
        Thread.sleep(5000);

        WebElement el = driver.findElement(By.xpath("//button[@data-auto=\"navProfileButton\"]/img"));
        el.click();

        el = driver.findElement(By.xpath("//input[@type='email']"));
        el.sendKeys("jasonroycwl028@mail.com");

        el = driver.findElement(By.xpath("//input[@type='password']"));
        el.sendKeys("Password!123");

        Thread.sleep(10000);

        el = driver.findElement(By.xpath("//button[@data-auto=\"authLoginButton\"]"));
        el.click();

        Thread.sleep(10000);

    }

    /**
     * Closes the {@link WebDriver} session.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        //driver.quit();
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
