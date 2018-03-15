package com.saucelabs;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LocalSafariTest {

    private WebDriver driver = null;

    @Before
    public void createDriver() {
        driver = new SafariDriver();
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void shouldBeAbleToPerformAGoogleSearch() throws InterruptedException {
        driver.get("http://www.google.com");
        WebElement el = driver.findElement(By.name("q"));
        el.sendKeys("webdriver");
        el.submit();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.titleIs("webdriver - Google Search"));
    }
}