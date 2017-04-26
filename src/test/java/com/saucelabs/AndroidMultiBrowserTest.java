package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class AndroidMultiBrowserTest implements SauceOnDemandSessionIdProvider {

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
     * Represents the deviceName to be used as part of the test run.
     */
    private String deviceName;
    /**
     * Represents the deviceType to be used as part of the test run.
     */
    private String deviceType;
    /**
     * Represents the version of Android to be used as part of the test run.
     */
    private String platformVersion;
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
     * Constructs a new instance of the test.  The constructor requires three string parameters, which represent the android
     * version, device type and device name to be used when launching a Sauce android emulator.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     * @param deviceName
     * @param platformVersion
     * @param deviceType
     */
    public AndroidMultiBrowserTest(String deviceName, String platformVersion, String deviceType) {
        super();
        this.deviceName = deviceName;
        this.platformVersion = platformVersion;
        this.deviceType = deviceType;
    }

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        browsers.add(new String[]{"Android Emulator", "4.4", "phone"});
        browsers.add(new String[]{"Android Emulator", "5.0", "phone"});
        browsers.add(new String[]{"Android Emulator", "5.1", "phone"});
        browsers.add(new String[]{"Android Emulator", "5.1", "tablet"});
        browsers.add(new String[]{"Android GoogleAPI Emulator", "5.0", "phone"});
        browsers.add(new String[]{"Samsung Galaxy S4 Emulator", "4.4", ""});
        return browsers;
    }


    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the {@link #deviceName},
     * {@link #platformVersion} instance variables, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver} instance.
     */
    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = DesiredCapabilities.android();
        capabilities.setCapability("deviceName", deviceName);
        if (deviceType != null) {
            capabilities.setCapability("deviceType", deviceType);
        }
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("appiumVersion", "1.5.3");
        capabilities.setCapability("browserName", "Browser");

        capabilities.setCapability("name", "Android Browser Test: " + deviceName + " " + deviceType + " " + platformVersion);
        //capabilities.setCapability("build", "testBuild");
        this.driver = new AndroidDriver<>(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void loadpage() throws Exception {
        driver.get("https://amazon.com");
        //driver.get("http://localhost:8080/examples/servlets/servlet/HelloWorldExample");


        //List<WebElement> anchors = driver.findElements(By.tagName("a"));
        //for (WebElement anchor: anchors) {
        //    System.out.println("anchor: " + anchor.getAttribute("outerHTML"));
        //}

        Thread.sleep(2000);

        assertTrue(driver.getTitle().startsWith("Amazon"));
        //assertTrue(driver.getTitle().startsWith("Hello"));
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
