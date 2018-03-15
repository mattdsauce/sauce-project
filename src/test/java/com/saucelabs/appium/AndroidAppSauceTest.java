package com.saucelabs.appium;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

public class AndroidAppSauceTest implements SauceOnDemandSessionIdProvider {

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

    String sauceUserName = authentication.getUsername();
    String sauceAccessKey = authentication.getAccessKey();

    /**
     * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
     * SAUCE_USERNAME and SAUCE_ACCESS_KEY environment variables to reference your Sauce account details.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("deviceOrientation","portrait");
        capabilities.setCapability("appiumVersion", "1.6.3");
        capabilities.setCapability("name", "Android Sauce App Test");
        capabilities.setCapability("app", "sauce-storage:NotesList.apk");

        driver = new AndroidDriver<>(new URL(MessageFormat.format("https://{0}:{1}@ondemand.saucelabs.com/wd/hub", sauceUserName, sauceAccessKey)),
                capabilities);
        this.sessionId = driver.getSessionId().toString();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


    @Test
    public void doTest() throws InterruptedException, IOException {

        WebElement el = driver.findElementByAccessibilityId("New note");
        el.click();

        el = driver.findElementByClassName("android.widget.EditText");
        el.sendKeys("This is a new note!");

        el = driver.findElementByAccessibilityId("Save");
        el.click();

        List<WebElement> els = driver.findElementsByClassName("android.widget.TextView");
        Assert.assertEquals(els.get(2).getText(), "This is a new note!");

        els.get(2).click();

        takeScreenshot("/Users/mattdunn/temp/scr1.jpg");

        Thread.sleep(10000);
    }

    public String getSessionId() {
        return sessionId;
    }
}