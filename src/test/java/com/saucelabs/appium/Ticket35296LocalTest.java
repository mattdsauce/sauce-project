package com.saucelabs.appium;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.text.MessageFormat;


public class Ticket35296LocalTest implements SauceOnDemandSessionIdProvider {

    private AppiumDriver<WebElement> driver;

    private String sessionId;

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
     * SAUCE_USERNAME and SAUCE_ACCESS_KEY environment variables to reference your Sauce account details.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        String sauceUserName = authentication.getUsername();
        String sauceAccessKey = authentication.getAccessKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "5.1");
        //capabilities.setCapability("deviceName", "Android Emulator");
        //capabilities.setCapability("deviceOrientation","portrait");
        //capabilities.setCapability("appiumVersion", "1.5.3");
        //capabilities.setCapability("name", "Ticket 35296 Test");
        //capabilities.setCapability("app", "sauce-storage:app-consumer-review.zip");
        //capabilities.setCapability("javascriptEnabled", true);

        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
        this.sessionId = driver.getSessionId().toString();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    @Test
    public void testApp() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver,20);

        // wait for login button and click it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.Button' and @text='Log In']")));
        driver.findElement(By.xpath("//*[@class='android.widget.Button' and @text='Log In']")).click();

        // wait for email field and populate it
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.EditText' and @text='ENTER EMAIL']")));
        driver.findElement(By.xpath("//*[@class='android.widget.EditText' and @text='ENTER EMAIL']")).sendKeys("170119080232@hqfuo.com");

        Thread.sleep(20000);

        // enter password
        driver.findElement(By.xpath("//*[@text='Password']/following-sibling::*[@class='android.widget.LinearLayout']//*[@class='android.widget.EditText']"))
                .sendKeys("test1234");

        // click login
        driver.findElement(By.xpath("//*[@class='android.widget.Button' and @text='Log In']")).click();


        Thread.sleep(15000);
    }

    public String getSessionId() {
        return sessionId;
    }
}