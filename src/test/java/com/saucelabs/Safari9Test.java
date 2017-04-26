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
import org.openqa.selenium.support.ui.SystemClock;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentParameterized.class)
public class Safari9Test implements SauceOnDemandSessionIdProvider {

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
    public Safari9Test(String os, String version, String browser) {
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
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
        browsers.add(new String[]{"OS X 10.11", "9.0", "safari"});
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
        capabilities.setCapability("commandTimeout", 20);
        capabilities.setCapability("name", "Safari 9 Test: " + browser + " " + version + ", " + os);
        //capabilities.setCapability("build", "testBuild");
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                //new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@localhost:4445/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void loadpage() throws Exception {
        driver.get("https://www.gofundme.com/parsen9?nocache=1");

        getWindowURL();
        List<WebElement> donate = driver.findElements(By.xpath("//a[contains(@href, '/donate') and contains(@class, 'btn')]"));
        WebElement donateBtn = donate.get(0);
        checkElem(donateBtn);
        getWindowURL();
        donateBtn.click();
        getWindowURL();

        WebElement amount = driver.findElement(By.id("input_amount"));
        checkElem(amount);
        amount.sendKeys("5");
        getWindowURL();

        WebElement firstName = driver.findElement(By.id("donate_firstname"));
        checkElem(firstName);
        firstName.sendKeys("Test");
        getWindowURL();

        WebElement lastName = driver.findElement(By.id("donate_lastname"));
        checkElem(lastName);
        lastName.sendKeys("Surname");
        getWindowURL();

        WebElement email = driver.findElement(By.id("donate_email"));
        checkElem(email);
        email.sendKeys("qaautomate@gofundme.com");
        getWindowURL();

        WebElement zip = driver.findElement(By.id("donate_zip_selector"));
        checkElem(zip);
        zip.sendKeys("92126");
        getWindowURL();

        WebElement comments = driver.findElement(By.xpath("//*[@name='Comments[text]']"));
        checkElem(comments);
        comments.sendKeys("This is a comment");
        getWindowURL();

        WebElement continueBtn = driver.findElement(By.xpath("//a[contains(@class, 'continuebtn')]"));
        checkElem(continueBtn);
        continueBtn.click();
        getWindowURL();

        WebElement ccNum = driver.findElement(By.xpath("//input[@name='billingCcNumber']"));
        checkElem(ccNum);
        ccNum.sendKeys("55");
        ccNum.sendKeys("63");
        ccNum.sendKeys("37");
        ccNum.sendKeys("40");
        ccNum.sendKeys("00");
        ccNum.sendKeys("10");
        ccNum.sendKeys("44");
        ccNum.sendKeys("45");
        getWindowURL();

        WebElement ccMonth = driver.findElement(By.xpath("//input[@name='billingCcMonth']"));
        checkElem(ccMonth);
        ccMonth.sendKeys("04");
        getWindowURL();

        WebElement ccYear = driver.findElement(By.xpath("//input[@name='billingCcYear']"));
        checkElem(ccYear);
        ccYear.sendKeys("19");
        getWindowURL();

        WebElement ccCVV = driver.findElement(By.xpath("//input[@name='billingCcCvv']"));
        checkElem(ccCVV);
        ccCVV.sendKeys("178");
        getWindowURL();

        WebElement billZip = driver.findElement(By.xpath("//input[@name='billingAddressZip']"));
        checkElem(billZip);
        billZip.clear();
        billZip.sendKeys("94063");
        getWindowURL();

        WebElement nextBtn = driver.findElement(By.xpath("//a[@id='btn-confirm-billing' or contains(@class,'js-donate-billing-next')]"));
        checkElem(nextBtn);
        nextBtn.click();
        getWindowURL();

        WebElement confirm = driver.findElement(By.xpath("//a[@id='btn-complete-receipt']"));
        checkElem(confirm);
        confirm.click();
        getWindowURL();

        getWindowURL();
        getWindowURL();
        getWindowURL();

        assertTrue(driver.getTitle().startsWith("GoFundMe"));
    }

    public void getWindowURL() {
        driver.getWindowHandle();
        driver.getWindowHandles();
        driver.getCurrentUrl();
    }

    public void checkElem(WebElement elem) {
        assertTrue(elem.isDisplayed());
        assertTrue(elem.isEnabled());
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
