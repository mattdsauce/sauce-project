package com.saucelabs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.URL;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class GwynnieBeeTOTest {

    private AppiumDriver driver;

    /* Takes care of sending the result of the tests over to TestObject. */
    @Rule
    public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

    /**
     * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
     * SAUCE_USERNAME and SAUCE_ACCESS_KEY environment variables to reference your Sauce account details.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", "49BEED40AA17440F9B8884B146898C62");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "4.4");
        //capabilities.setCapability("testobject_appium_version", "1.6.4");

        driver = new AndroidDriver(new URL("http://eu1.appium.testobject.com/wd/hub"), capabilities);
        //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        resultWatcher.setRemoteWebDriver(driver);
    }

    @Test
    public void testMethod() throws Exception {

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
        el.sendKeys("test+precondition+subscriber+1497951684184@gwynniebee.com");

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

        Thread.sleep(5000);

    }

}