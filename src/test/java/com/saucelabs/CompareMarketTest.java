package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class CompareMarketTest implements SauceOnDemandSessionIdProvider {

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
     * Instance variable which contains the Sauce Job Id.
     */
    private String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;


    @Before
    public void setUp() throws Exception {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appiumVersion", "1.5.3");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "9.2");
        caps.setCapability("browserName", "safari");
        caps.setCapability("deviceName","iPhone Simulator");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("safariAllowPopups", true);
        caps.setCapability("safariOpenLinksInBackground", true);
        caps.setCapability("name", "Compare The Market iOS Test");
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                //new URL("http://localhost:4723/wd/hub"),
                caps);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        this.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

    }

    /**
     * Runs a simple test.
     * @throws Exception
     */
    @Test
    public void loadPage() throws Exception {

        driver.get("https://quote.comparethemarket.com/LifeInsurance/Home/YourDetails?AFFCLIE=TSTM");

        WebElement el = driver.findElement(By.id("yearsTextBox"));
        el.sendKeys("20");

        el = driver.findElement(By.id("amount"));
        el.sendKeys("20000");

        el = driver.findElement(By.id("proposerTitle"));
        Select dropdown = new Select(el);
        dropdown.selectByVisibleText("Miss");

        el = driver.findElement(By.id("proposerFirstname"));
        el.sendKeys("Test");

        el = driver.findElement(By.id("proposerSurname"));
        el.sendKeys("Test");

        el = driver.findElement(By.id("proposerDateOfBirthDay"));
        dropdown = new Select(el);
        dropdown.selectByVisibleText("26");

        el = driver.findElement(By.id("proposerDateOfBirthMonth"));
        dropdown = new Select(el);
        dropdown.selectByVisibleText("May");

        el = driver.findElement(By.id("proposerDateOfBirthYear"));
        dropdown = new Select(el);
        dropdown.selectByVisibleText("1983");

        el = driver.findElement(By.id("proposerNonSmokerLabel"));
        el.click();

        el = driver.findElement(By.id("email"));
        el.sendKeys("pcarines@emailreaction.org");

        el = driver.findElement(By.id("houseNumber"));
        el.sendKeys("2");

        el = driver.findElement(By.id("postcode"));
        el.sendKeys("pe26xj");

        el = driver.findElement(By.id("findAddress"));
        el.click();

        el = driver.findElement(By.id("addressList"));
        dropdown = new Select(el);
        dropdown.selectByVisibleText("B G L Group Ltd");

        el = driver.findElement(By.cssSelector(".email"));
        el.click();

        el = driver.findElement(By.cssSelector(".post"));
        el.click();

        el = driver.findElement(By.id("agreeTermsLabel"));
        el.click();

        el = driver.findElement(By.id("submitPolicyDetails"));
        el.click();

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Grid-quote")));

        el = driver.findElement(By.id("highlight-my-account-close"));
        el.click();

        el = driver.findElement(By.id("highlight-my-account-close"));
        el.click();

        List<WebElement> els = driver.findElements(By.id("LIFE_moreDetails_0"));
        for (WebElement elem: els) {
            System.out.println(elem.getTagName());
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
            Thread.sleep(500);
            if (elem.isDisplayed()) {
                elem.click();
            }
        }

        el = driver.findElement(By.id("PolicyDocuments-anchor--0"));
        el.click();

        for(String winHandle : driver.getWindowHandles()){
            System.out.println("window handle: " + winHandle);
        }

        Thread.sleep(20000);

        //assertTrue(driver.getTitle().startsWith("Life"));

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
}
