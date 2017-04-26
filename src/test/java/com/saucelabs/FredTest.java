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
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class FredTest implements SauceOnDemandSessionIdProvider {

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
    public FredTest(String os, String version, String browser) {
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
        //browsers.add(new String[]{"macOS 10.12", "latest", "firefox"});
        //browsers.add(new String[]{"Windows 10", "11", "internet explorer"});
        //browsers.add(new String[]{"Windows 10", "14", "MicrosoftEdge"});
        //browsers.add(new String[]{"Windows 10", "50", "firefox"});
        //browsers.add(new String[]{"windows 7", "55", "chrome"});
        //browsers.add(new String[]{"windows 8.1", "latest", "chrome"});
        //browsers.add(new String[]{"windows 8.1", "56", "chrome"});
        //browsers.add(new String[]{"windows 8.1", "55", "chrome"});
        //browsers.add(new String[]{"linux", "latest", "firefox"});
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
        //capabilities.setCapability("javascriptEnabled",true);
        //capabilities.setCapability("tunnelIdentifier", "mattTunnel");
        //capabilities.setCapability("seleniumVersion", "3.3.1");
        //capabilities.setCapability("iedriverVersion", "3.3.0");
        //capabilities.setCapability("chromedriverVersion", "2.29");
        //capabilities.setCapability("captureHtml",true);
        //capabilities.setCapability("marionette", false);
        //capabilities.setCapability("avoidProxy", true);
        //capabilities.setCapability("unexpectedAlertBehaviour", "ignore");
        //capabilities.setCapability("timeZone", "London");
        //capabilities.setCapability("public", "private");
        capabilities.setCapability("name", "Fred Test: " + browser + " " + version + ", " + os);
        //capabilities.setCapability("build", "testBuild");
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                //new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@localhost:4445/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    public void elementHighlight(WebElement element) {
        for (int i = 0; i < 2; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "color: blue; border: 3px solid blue;");
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "");
        }
    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void testMethod() throws Exception {
        driver.get("https://fredhub-qa.azurewebsites.net/");

        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cred_userid_inputtext")));

        WebElement el = driver.findElement(By.id("cred_userid_inputtext"));
        el.sendKeys("automationqa1@fred.com.au");

        el = driver.findElement(By.id("cred_password_inputtext"));
        el.sendKeys("dRAwrepr4ja8");

        el = driver.findElement(By.id("cred_sign_in_button"));
        assertTrue(el.isDisplayed());
        assertTrue(el.isEnabled());
        el.click();
        el.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dashboard-home-badge")));

        el = driver.findElement(By.className("dashboard-home-badge"));
        el.click();

        el = driver.findElement(By.id("Promotions"));
        el.click();

        el = driver.findElement(By.id("New Promotion"));
        el.click();

        el = driver.findElement(By.cssSelector("*[class$='window_stack']"));
        assertTrue(el.isDisplayed());

        //el = driver.findElement(By.cssSelector("[formcontrolname = 'dateRange']"));
        //elementHighlight(el);
        //el = driver.findElement(By.xpath("/html/body/cmp-app-root/cmp-app-root/cmp-desktop/cmp-dashboard-windows/div/div/cmp-window[1]/div/div[2]/div/ng-component/div[1]/form/div/div[1]/div[1]/cmp-promotion-general/form/cmp-date-range-picker/div/input"));
        el = driver.findElement(By.cssSelector("body > cmp-app-root > cmp-app-root > cmp-desktop > cmp-dashboard-windows > div > div > cmp-window:nth-child(2) > div > div.window-content > div > ng-component > div.fo-promotions > form > div > div.fo-promotions-create-header > div.fo-promotions-create-details > cmp-promotion-general > form > cmp-date-range-picker > div > input\n"));
        el.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".calendar.left")));
        el = driver.findElement(By.cssSelector(".calendar.left"));
        assertTrue(el.isDisplayed());



        Thread.sleep(2000);

        assertTrue(driver.getTitle().startsWith("Fred"));
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
