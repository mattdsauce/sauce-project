package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SafariTabTest implements SauceOnDemandSessionIdProvider {

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


    @Before
    public void setUp() throws Exception {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appiumVersion", "1.7.1");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "10.3");
        caps.setCapability("deviceName","iPhone Simulator");
        //caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("browserName", "safari");
        caps.setCapability("safariAllowPopups", true);
        caps.setCapability("automationName", "XCUITest");
        caps.setCapability("name", "iPhone Simulator safari tab test");
        this.driver = new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                caps);
        //this.driver = new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"), caps);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

    }

    /**
     * Runs a simple test
     * @throws Exception
     */
    @Test
    public void loadPage() throws Exception {

        System.out.println("START TIME" + new Date() + "\n");
        System.out.println("Open url");
        driver.get("http://mobile.newsnow.uk");
        System.out.println("Opened URL");

        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div/div[2]/div/div/div[1]/div[1]/div/span[2]/a[1]")).click(); //opens the first news in a new tab
        System.out.println("Clicked on link");
        driver.switchTo().alert().accept();

        Set<String> windows = driver.getWindowHandles();
        Object[] arrWindow = windows.toArray();
        if (arrWindow.length > 0) {
            driver.switchTo().window(arrWindow[0].toString());
        }

        Thread.sleep(5000);



        Assert.assertNotNull(driver.getTitle());
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
