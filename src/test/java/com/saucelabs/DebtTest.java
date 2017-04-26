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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@RunWith(ConcurrentParameterized.class)
public class DebtTest implements SauceOnDemandSessionIdProvider {

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
     * Represents the device to be used as part of the test run.
     */
    private String device;
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
     * @param device
     */
    public DebtTest(String os, String version, String browser, String device) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
        this.device = device;
    }

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        browsers.add(new String[]{"iOS", "9.3", "safari", "iPhone Simulator"});
        browsers.add(new String[]{"iOS", "9.3", "safari", "iPhone Simulator"});
        browsers.add(new String[]{"iOS", "9.3", "safari", "iPhone Simulator"});
        //browsers.add(new String[]{"iOS", "10.0", "safari", "iPhone Simulator"});
        //browsers.add(new String[]{"iOS", "10.0", "safari", "iPhone Simulator"});
        //browsers.add(new String[]{"iOS", "10.0", "safari", "iPhone Simulator"});
        //browsers.add(new String[]{"iOS", "10.2", "safari", "iPhone Simulator"});
        //browsers.add(new String[]{"iOS", "10.2", "safari", "iPhone Simulator"});
        //browsers.add(new String[]{"iOS", "10.2", "safari", "iPhone Simulator"});
        //browsers.add(new String[]{"iOS", "10.2", "safari", "iPhone Simulator"});
        return browsers;
    }

    private HashMap<String, String> map = new HashMap<String, String>();
    WebElement element = null;


    @Before
    public void setUp() throws Exception {

        DesiredCapabilities caps = DesiredCapabilities.iphone();
        caps.setCapability("appiumVersion", "1.5.3");
        caps.setCapability("deviceName", device);
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("browserName", browser);
        caps.setCapability("platformVersion", version);
        caps.setCapability("platformName", os);
        caps.setCapability("fullReset", true);
        caps.setCapability("sendKeyStrategy", "grouped");
        caps.setCapability("safariAllowPopups", true);
        caps.setCapability("safariOpenLinksInBackground", true);
        caps.setCapability("idleTimeout", 180);
        caps.setCapability("maxDuration", 3600);
        caps.setCapability("name", "Debt Test " + os + " " + version + " " + device + " " + browser);
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                //new URL("http://localhost:4723/wd/hub"),
                caps);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        this.driver.manage().timeouts().pageLoadTimeout(1200, TimeUnit.SECONDS);

    }

    /**
     * Runs a simple test.
     * @throws Exception
     */
    @Test
    public void runTest() throws Exception {

        String uri = "http://www.lowermybills.com/debt/?force=5096&force=5403&profile=bypassall";

        driver.get(uri);

        waitForElementVisible(By.id("debtType"), driver, map);
        selectText(By.xpath("//*[@name = 'creditCardDebt']"), "12,500 – 14,999", driver, map);
        selectText(By.cssSelector("#unsecuredDebt"), "10,000 – 12,499", driver, map);
        sendKeys(By.id("consumerZipCode"), "consumerZipCode", "93633", driver, map);
        submit(By.id("button"), driver, map);
        sleep(20000); //Issue may occur here

        waitForElementVisible(By.xpath("//*[@name = 'consumerTitle']"), driver, map);
        selectText(By.xpath("//*[@name = 'consumerTitle']"), "Mr.", driver, map);
        selectText(By.xpath("//*[@name = 'consumerAge']"), "20 - 29", driver, map);
        sendKeys(By.cssSelector("#consumerFirstName"), "Mark", driver, map);
        sendKeys(By.cssSelector("#consumerLastName"), "Dawson", driver, map);
        sendKeys(By.cssSelector("#dayPhoneCombined"), "3103486900", driver, map);
        sendKeys(By.cssSelector("#consumerEmailAddress"), "site_check@lowermybills.com", driver, map);
        submit(By.id("button"), driver, map);
        sleep(20000); //Issue may occur here

        waitForElementVisible(By.cssSelector("#debtSituation"), driver, map);
        click(By.xpath("//*[@name = 'creditorsMajorCreditCards']"), driver, map);
        click(By.xpath("//*[@name = 'creditorsOtherBills']"), driver, map);
        comboboxWaitForItem(By.cssSelector("#debtSituation"), "About to fall behind", 4, driver, map);
        selectText(By.cssSelector("#debtSituation"), "About to fall behind", driver, map);
        comboboxWaitForItem(By.cssSelector("#highestInterestRate"), "0 to 10%", 4, driver, map);
        selectText(By.cssSelector("#highestInterestRate"), "0 to 10%", driver, map);
        comboboxWaitForItem(By.cssSelector("#sourceOfIncome"), "Full time job", 4, driver, map);
        selectText(By.cssSelector("#sourceOfIncome"), "Full time job", driver, map);
        comboboxWaitForItem(By.cssSelector("#whyVisitedLMB"), "To settle my debt", 4, driver, map);
        selectText(By.cssSelector("#whyVisitedLMB"), "To settle my debt", driver, map);
        selectText(By.cssSelector("#city"), "CA - Kings Canyon National Pk", driver, map);
        sendKeys(By.xpath("//*[@name = 'consumerStreet']"), "123 state st", driver, map);
        submit(By.id("button"), driver, map);
        sleep(20000); //Issue may occur here

        waitForElementVisible(By.id("matchContent"), driver, map);

    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
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

    public void waitForElementToExist(By locator, WebDriver driver, HashMap<String, String> map) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForElementVisible(By locator, WebDriver driver, HashMap<String, String> map) {
        waitForElementToExist(locator, driver, map);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void selectText(By locator, String onDemandValue, WebDriver driver, HashMap<String, String> map)
            throws Exception {
        waitForElementVisible(locator, driver, map);
        element = findElementBy(locator, driver, map);
        Select selectElement = new Select(element);
        selectElement.selectByVisibleText(onDemandValue);
    }

    public WebElement findElementBy(By locator, WebDriver driver, HashMap<String, String> map) {
        element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
        return element;
    }

    public void sendKeys(By locator, String contentString, String onDemandValue, WebDriver driver,
                         HashMap<String, String> map) {
        waitForElementVisible(locator, driver, map);
        element = findElementBy(locator, driver, map);
        sleep(1000);
        element.clear();
        element.sendKeys(onDemandValue);
        String contentString1 = "document.getElementById('" + contentString + "').value='" + onDemandValue + "'";
        contentString = "context.application." + contentString + "='" + onDemandValue + "'";
        ((JavascriptExecutor) driver).executeScript(contentString1);
        ((JavascriptExecutor) driver).executeScript(contentString);
    }

    public void sendKeys(By locator, String onDemandValue, WebDriver driver, HashMap<String, String> map) {
        waitForElementVisible(locator, driver, map);
        element = findElementBy(locator, driver, map);
        sleep(1000);
        element.clear();
        element.sendKeys(onDemandValue);
    }

    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void submit(By locator, WebDriver driver, HashMap<String, String> map) {
        waitForElementVisible(locator, driver, map);
        element = findElementBy(locator, driver, map);
        sleep(1000);
        element.submit();
    }

    public void click(By locator, WebDriver driver, HashMap<String, String> map) {
        waitForElementVisible(locator, driver, map);
        element = findElementBy(locator, driver, map);
        element.click();
    }

    public void comboboxWaitForItem(By locator, String onDemandValue, int timeToWait, WebDriver driver,
                                    HashMap<String, String> map) throws Exception {
        boolean elementTextFound = false;
        waitForElementToExist(locator, driver, map);
        element = findElementBy(locator, driver, map);
        Select selectElement = new Select(element);
        if (timeToWait < 1) {
            timeToWait = 10;
        }
        for (int i = 0; i < timeToWait; i++) {
            for (WebElement optionElement : selectElement.getOptions()) {
                String optionElementString = optionElement.getText();
                if (onDemandValue.equals(optionElementString)) {
                    elementTextFound = true;
                }
            }
            if (elementTextFound == true) {
                break;
            }
            sleep(1000);
        }
    }
}
