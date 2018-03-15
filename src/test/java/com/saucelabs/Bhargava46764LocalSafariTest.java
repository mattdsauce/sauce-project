package com.saucelabs;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class Bhargava46764LocalSafariTest {

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
    public void doTest() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);

        System.out.println("Open Website");
        driver.get("http://scorebuzz.000webhostapp.com/");
        Thread.sleep(5000);

        driver.findElement(By.id("firstname")).sendKeys("pravallikapanuganti@gmail.com");
        String ele = driver.findElement(By.id("firstname")).getAttribute("maxlength");
        System.out.println(ele);
        Thread.sleep(15000);
    }
}