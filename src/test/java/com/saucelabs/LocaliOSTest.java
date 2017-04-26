package com.saucelabs;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class LocaliOSTest {


    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;

    private static final String CSS_SELECTOR_DEPARTURE_FIELD = ".rp-Reisplanbalk__location.rp-Reisplanbalk__location--departure input";


    @Before
    public void setUp() throws Exception {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "safari");
        caps.setCapability("deviceName", "iPhone 6");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("autoAcceptAlerts", true);
        caps.setCapability("locationServicesEnabled", true);

        this.driver = new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"), caps);
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @Test
    public void typeEnterIntoInputField() throws Exception {

        driver.get("http://www.ns.nl/reisplanner-alpha");
        new WebDriverWait(driver, 4, 500).until(
                (ExpectedCondition<WebElement>) input -> driver.findElement(
                        By.cssSelector(CSS_SELECTOR_DEPARTURE_FIELD)));

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

        Thread.sleep(30000);

        String inputValue = inputField.getAttribute("value");
        assertTrue("Input value is set to Amsterdam", "Amsterdam".equals(inputValue));
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


}
