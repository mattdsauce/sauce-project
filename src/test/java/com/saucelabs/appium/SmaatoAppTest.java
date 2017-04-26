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

import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class SmaatoAppTest implements SauceOnDemandSessionIdProvider {

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
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("deviceOrientation","portrait");
        capabilities.setCapability("appiumVersion", "1.5.3");
        //capabilities.setCapability("automationName", "Selendroid");
        capabilities.setCapability("name", "Smaato App Test");
        capabilities.setCapability("app", "sauce-storage:SmaatoDemoAppAndroid.apk");
        capabilities.setCapability("javascriptEnabled", true);

        driver = new AndroidDriver<>(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUserName, sauceAccessKey)),
                capabilities);
        this.sessionId = driver.getSessionId().toString();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    @Test
    public void smaatoDemo() throws InterruptedException {

        WebElement el = driver.findElement(By.xpath("//android.widget.Button[1]"));
        el.click();

        el = driver.findElement(By.xpath("//android.widget.EditText[1]"));
        el.sendKeys("5e6c9dadc5754dae91ea13052c995bd0");

        el = driver.findElement(By.id("com.smaato.demointegration:id/loadBanner"));
        el.click();

        for(String context : driver.getContextHandles()){
            System.out.println("available context handle: " + context);
        }
        System.out.println("current context: " + driver.getContext());

        ((AppiumDriver)driver).context("WEBVIEW_com.smaato.demointegration");

        el = driver.findElement(By.tagName("iframe"));
        System.out.println("iframe source: " + el.getAttribute("innerHTML"));

        Thread.sleep(15000);
    }

    public String getSessionId() {
        return sessionId;
    }
}