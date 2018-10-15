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
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
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
public class BBVAActionsTest implements SauceOnDemandSessionIdProvider {

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
    public BBVAActionsTest(String os, String version, String browser) {
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
        //browsers.add(new String[]{"Windows 10", "latest", "firefox"});
        //browsers.add(new String[]{"macOS 10.13", "latest", "chrome"});
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
        capabilities.setCapability("seleniumVersion","3.11.0");
        capabilities.setCapability("name", "Actions Test: " + browser + " " + version + ", " + os);
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
    public void loadpage() throws Exception {

        /*driver.get("https://qa.bbvaapimarket.com/login");
        WebElement el = driver.findElement(By.id("overridelink"));
        el.click();

        el = waitForElementById("btnApiMarketEs");
        el.click();

        el = waitForElementByXPath("//input[@id='form-login-username']");
        el.click();
        el.clear();
        el.sendKeys("Dev2EntiQAIE");

        el = waitForElementByXPath("//input[@id='form-login-password']");
        el.click();
        el.clear();
        el.sendKeys("Asdf1234");

        el = waitForElementByXPath("//paper-button[@id='btnid_login-submit-button']");
        el.click();

        el = waitForElementByCss("amkt\\\\-modal");

        el = waitForElementByCss("amkt-modal");

        el = waitForElementByXPath("/*//*[@id='loggedUserMenu']");
        hoverOverElement(el);*/

        driver.get("http://ianlunn.github.io/Hover/");
        WebElement el = waitForElementByClass("hvr-pulse");
        hoverOverElement(el);


        Thread.sleep(20000);


    }

    public WebElement waitForElementByClass(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locator)));
        return driver.findElement(By.className(locator));
    }

    public WebElement waitForElementByCss(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
        return driver.findElement(By.cssSelector(locator));
    }

    public WebElement waitForElementById(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
        return driver.findElement(By.id(locator));
    }

    public WebElement waitForElementByXPath(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        return driver.findElement(By.xpath(locator));
    }


    public void hoverOverElement(WebElement element) {
        try {
            //click(element);
            Actions action = new Actions(driver);
            Action mouseOver = action.moveToElement(element).build();
            mouseOver.perform();
        } catch (UnsupportedCommandException e) {
            System.out.println("Hover Action not supported, trying with JS");
            jsHoverOverElement(element);
        }
    }

    public void jsHoverOverElement(WebElement element) {
        String script = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";
        executeScriptOnElement(script, element);
        focus(element);
    }

    public void focus(WebElement element) {
        String script = "arguments[0].focus();return true;";
        executeScriptOnElement(script, element);
    }

    public void executeScriptOnElement(String script, WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(script, element);
    }

    public void jsClick(WebElement element) {
        String script = "arguments[0].click();";
        executeScriptOnElement(script, element);
    }

    public void click(WebElement element) {
        try {
            if (element.getAttribute("href") != null && element.getAttribute("href").contains("javascript:void(0)")) {
                jsClick(element);
            } else {
                clickelement(element);
            }
        } catch (WebDriverException e) {
            //scrollIntoElementJS(element);
            clickelement(element);
        }

    }

    private  void clickelement(WebElement element) {
        try {
            if ((element.getAttribute("href") != null && element.getAttribute("href").contains("javascript:void(0)")) || browser.contains("internet explorer")) {
                jsClick(element);
            } else {
                element.click();
            }
        } catch (ElementNotInteractableException e) {
            jsClick(element);
        }
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
