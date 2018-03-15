package com.saucelabs;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class Bhargava46764LocaliOSTest {


    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "safari");
        caps.setCapability("deviceName", "iPhone 6");
        caps.setCapability("automationName","XCUITest");

        this.driver = new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"), caps);
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @Test
    public void typeEnterIntoInputField() throws Exception {

        driver.manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver,30);

        System.out.println("Open Website");
        driver.get("http://scorebuzz.000webhostapp.com/");
        Thread.sleep(5000);

        driver.findElement(By.id("firstname")).sendKeys("pravallikapanuganti@gmail.com");
        String ele = driver.findElement(By.id("firstname")).getAttribute("maxlength");
        System.out.println(ele);
        Thread.sleep(10000);
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
