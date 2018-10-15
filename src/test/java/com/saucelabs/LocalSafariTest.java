package com.saucelabs;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


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
    public void doTest() throws InterruptedException, IOException {
        /*driver.get("http://www.google.com");
        WebElement el = driver.findElement(By.name("q"));
        el.sendKeys("webdriver");
        el.submit();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.titleIs("webdriver - Google Search"));*/

        driver.get("https://xd.adobe.com/view/0994c935-7994-4191-6551-d8e00f48ed3b/?uiAutomation");

        Thread.sleep(2000);

        takeScreenshot("/Users/mattdunn/temp/scr1.png");

        Thread.sleep(2000);

    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }
}