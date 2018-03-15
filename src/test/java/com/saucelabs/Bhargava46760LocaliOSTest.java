package com.saucelabs;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Bhargava46760LocaliOSTest {


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

        System.out.println("Open Website");
        driver.get("https://www.staples.com");
        Thread.sleep(5000);

        WebElement el = driver.findElement(By.xpath("//button[@data-auto=\"navProfileButton\"]/img"));
        el.click();

        el = driver.findElement(By.xpath("//input[@type='email']"));
        el.sendKeys("jasonroycwl028@mail.com");

        el = driver.findElement(By.xpath("//input[@type='password']"));
        el.sendKeys("Password!123");

        Thread.sleep(10000);

        el = driver.findElement(By.xpath("//button[@data-auto=\"authLoginButton\"]"));
        el.click();

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
