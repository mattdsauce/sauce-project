package com.saucelabs;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import static org.junit.Assert.assertTrue;

public class TestForIOS {
    private static final String USER = System.getenv("SAUCE_USERNAME");
    private static final String KEY = System.getenv("SAUCE_ACCESS_KEY");

    private static final String CSS_SELECTOR_DEPARTURE_FIELD = ".rp-Reisplanbalk__location.rp-Reisplanbalk__location--departure input";

    @Rule
    public TestName testName = new TestName();

    private RemoteWebDriver driver;

    @Before
    public void setUp() throws Exception {
        /**DesiredCapabilities caps = DesiredCapabilities.iphone();
        caps.setCapability("platform", "OS X 10.10");
        caps.setCapability("version", "8.0");
        caps.setCapability("deviceName", "iPhone 4s");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("appiumVersion", "1.5.3");
        //caps.setCapability("tunnelIdentifier", "<YOUR_TUNNEL>");
        **/
        DesiredCapabilities caps = DesiredCapabilities.safari();
        caps.setCapability("platform", "OS X 10.11");
        caps.setCapability("version", "9.0");
        caps.setCapability("name", testName.getMethodName());

        driver = new RemoteWebDriver(
                new URL(String.format("http://%s:%s@ondemand.saucelabs.com:80/wd/hub", USER, KEY)), caps);
        driver.get("http://www.ns.nl/reisplanner-alpha");
        new WebDriverWait(driver, 4, 500).until(
                (ExpectedCondition<WebElement>) input -> driver.findElement(
                        By.cssSelector(CSS_SELECTOR_DEPARTURE_FIELD)));

    }

    @Test
    public void typeEnterIntoInputField() throws Exception {
        WebElement inputField = driver.findElement(By.cssSelector(CSS_SELECTOR_DEPARTURE_FIELD));
        inputField.sendKeys("ams");

        new WebDriverWait(driver, 4, 500).until(
                (ExpectedCondition<WebElement>) input -> driver.findElement(By.cssSelector(".dropdown-menu")));

        WebElement autosuggestItems = driver.findElement(By.cssSelector(".autosuggest__listItem"));
        assertTrue("Autosuggest contains Amsterdam", autosuggestItems.getText().contains("Amsterdam"));


        try {
            autosuggestItems.sendKeys(Keys.RETURN);
        } catch (WebDriverException e) {
            if (e.getMessage().contains("Element is no longer attached to the DOM ")) {
                // as soon as we send RETURN to this element, it's gone, so we get a "Element is no longer attached to the DOM" error - ignore
            } else {
                throw e;
            }
        }
        //inputField.sendKeys(Keys.RETURN);

        String inputValue = inputField.getAttribute("value");
        assertTrue("Input value is set to Amsterdam", "Amsterdam".equals(inputValue));
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}

