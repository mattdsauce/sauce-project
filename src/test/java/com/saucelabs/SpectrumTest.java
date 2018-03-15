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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class SpectrumTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(System.getenv("SAUCE_USERNAME"), System.getenv("SAUCE_ACCESS_KEY"));
    //public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("mattdsauce-sub2", "cc17f8ee-bb85-44cd-be25-413721dcaee1");

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
    public SpectrumTest(String os, String version, String browser) {
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
        browsers.add(new String[]{"macOS 10.12", "10.0", "safari"});
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
        HashMap<String,Object> prerun = new HashMap<String,Object>();
        prerun.put("executable","https://gist.githubusercontent.com/saucyallison/3a73a4e0736e556c990d/raw/d26b0195d48b404628fc12342cb97f1fc5ff58ec/disable_fraud.sh");
        prerun.put("background",false);
        prerun.put("timeout","120");
        capabilities.setCapability("prerun", prerun);
        capabilities.setCapability("safariIgnoreFraudWarning", true);
        capabilities.setCapability("screenResolution", "1400x1050");
        capabilities.setCapability("name", "Spectrum Routing Number Test: " + browser + " " + version + ", " + os);
        this.driver = new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void doTest() throws Exception {
        driver.get("http://charternet:Chart3rn3t@www.engnew-spectrum.net");

        // click 'Sign In' link
        WebElement el = driver.findElement(By.xpath("//li/a[contains(text(),'Sign In')]"));
        el.click();

        // log in
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cc-username']")));
        el = driver.findElement(By.xpath("//*[@id='cc-username']"));
        el.sendKeys("virtualhomestandard");


        el = driver.findElement(By.xpath("//input[@ng-model='form.password']"));
        el.sendKeys("Sit_test01");

        el = driver.findElement(By.id("login-form-button"));
        elementHighlight(el);
        el.click();
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("sauce: break");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"page-header\"]/div/div/div/div[1]/ul/li[1]/span/a")));
        el = driver.findElement(By.xpath("//*[@id=\"page-header\"]/div/div/div/div[1]/ul/li[1]/span/a"));
        assertTrue(el.getText().equals("virtualhomestandard"));

        Thread.sleep(120000);

        assertTrue(driver.getTitle().startsWith("Spectrum"));
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


    public void elementHighlight(WebElement element) {
        for (int i = 0; i < 2; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "color: red; border: 3px solid red;");
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "");
        }
    }
}
