package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class DragDropTest implements SauceOnDemandSessionIdProvider {

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

    public DragDropTest() {
        super();
    }

    final String host = System.getProperty("host", "saucelabs");
    final String browser = System.getProperty("browser", "firefox");
    final String browserVersion = System.getProperty("browserVersion", "58");
    final String platform = System.getProperty("platform", "Windows 10");

    @Before
    public void setUp() throws Exception {

        if (host.equals("saucelabs")) {

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", browser);
            capabilities.setCapability("version", browserVersion);
            capabilities.setCapability("platform", platform);
            capabilities.setCapability("name", "Drag Drop Test");
            capabilities.setCapability("seleniumVersion", "3.8.1");
            String sauceUrl = String.format("http://%s:%s@ondemand.saucelabs.com:80/wd/hub",
                    authentication.getUsername(), authentication.getAccessKey());
            driver = new RemoteWebDriver(new URL(sauceUrl), capabilities);
            sessionId = ((RemoteWebDriver) driver).getSessionId().toString();

        } else if (host.equals("localhost")) {

            if (browser.equals("firefox")) {
                driver = new FirefoxDriver();
            } else if (browser.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver",
                        System.getProperty("user.dir") + "/vendor/chromedriver");
                driver = new ChromeDriver();
            }

        }

    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void dragDropTest() throws Exception {
        driver.get("http://the-internet.herokuapp.com/drag_and_drop");

        WebElement source = driver.findElement(By.id("column-a"));
        WebElement target = driver.findElement(By.id("column-b"));

        new Actions(driver).dragAndDrop(source, target).build().perform();

        Thread.sleep(10000);

        assertEquals("B", source.getText());
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
