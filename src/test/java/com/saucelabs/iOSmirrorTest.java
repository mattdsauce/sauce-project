package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class iOSmirrorTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("mattdsauce", "fc7530ea-9891-4618-aa52-5460e4beb094");

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

    public iOSmirrorTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities caps = DesiredCapabilities.iphone();
        caps.setCapability("platform", "OS X 10.10");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "Safari");
        caps.setCapability("version", "8.4");
        caps.setCapability("deviceName","iPhone 6 Plus");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("name", "iOS Mirror Test");
        //this.driver = new RemoteWebDriver(
        //        new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
        //        caps);
        this.driver = new RemoteWebDriver(
                new URL("http://127.0.0.1:4723/wd/hub"), caps);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void mirror() throws Exception {
        // get page
        driver.get("http://www.mirror.co.uk/sport/football/news/manchester-citys-manuel-pellegrini-wins-6421875?service=tablet");
        // find and click Login button
        WebElement loginButton = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='gig-composebox-site-login']")));
        loginButton.click();
        // get 5 social media buttons
        //List<WebElement> buttons = (new WebDriverWait(driver, 10))
        //        .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".gigya-login-provider>div")));
        // get and click the first which is the Facebook button
        //WebElement button = buttons.get(0);
        WebElement button = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"gig_1442315971638_showScreenSet_social_0_uiContainer\"]/table/tbody/tr/td/div/div/div[1]/span[1]")));
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(button));
        button.click();
        // wait for facebook login page to load
        WebElement fbLoginForm = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#login_form")));


        assertEquals("Manchester City's Manuel Pellegrini wins Barclays Premier League manager of the month for August - Mirror Online", driver.getTitle());
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
