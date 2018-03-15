package com.saucelabs.appium;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;


public class GwynnieBeeTest implements SauceOnDemandSessionIdProvider {

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

        String appPath = "/Users/mattdunn/temp/app.apk";
        boolean uploadApp = false;

        if (uploadApp) {
            // use SauceREST to upload app to sauce storage
            SauceREST r = new SauceREST(sauceUserName, sauceAccessKey);
            File f = new File(appPath);
            String response = r.uploadFile(f, "app.apk", true);
            System.out.println("App upload response: " + response);
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability("deviceName", "Android GoogleAPI Emulator");
        capabilities.setCapability("deviceOrientation","portrait");
        capabilities.setCapability("appiumVersion", "1.6.4");
        capabilities.setCapability("name", "GwynnieBee App Test");
        capabilities.setCapability("app", "sauce-storage:app.apk");
        capabilities.setCapability("appPackage", "com.gwynniebee.branch");
        capabilities.setCapability("appActivity", "com.gwynniebee.gbcloset.login.ui.SplashPageActivity");

        driver = new AndroidDriver<>(new URL(MessageFormat.format("https://{0}:{1}@ondemand.saucelabs.com/wd/hub", sauceUserName, sauceAccessKey)),
                capabilities);
        driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);
        this.sessionId = driver.getSessionId().toString();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    @Test
    public void testMethod() throws InterruptedException {

        WebElement el;

        try {
            el = driver.findElementById("button1");
            el.click();
        } catch (Exception e) {
            // nothing
        }

        el = driver.findElementById("option_login");
        el.click();

        assertTrue(driver.findElementById("log_in_button").isDisplayed());

        el = driver.findElementById("email_edit_text");
        el.sendKeys("test+precondition+subscriber+1493308415863@gwynniebee.com");

        el = driver.findElementById("password_edit_text");
        el.sendKeys("123456");

        el = driver.findElementById("log_in_button");
        el.click();

        el = driver.findElementById("btn_yes");
        el.click();

        el = driver.findElementById("home_activity_menu_icon");
        el.click();

        el = driver.findElementById("my_account");
        el.click();

        Thread.sleep(10000);

        String current_context = driver.getContext();
        String webview_context = null;
        Set<String> ContextHandles = driver.getContextHandles();
        for (String ContextHandle : ContextHandles) {
            if (ContextHandle.contains("WEBVIEW")) {
                webview_context = ContextHandle;
            }
        }
        driver.context(webview_context);

        el = driver.findElementByCssSelector("#customer_membership_details div.toggler");
        el.click();


        Thread.sleep(10000);
    }


    public String getSessionId() {
        return sessionId;
    }
}