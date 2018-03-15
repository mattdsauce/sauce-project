package com.saucelabs;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class LocalFirefoxTest {

    @Test
    public void testUpload() throws InterruptedException {

        System.setProperty("webdriver.gecko.driver", "/Users/mattdunn/dev/geckodrivers/0.18.0/geckodriver");

        WebDriver driver = new FirefoxDriver();
        //driver.get("http://sl-test.herokuapp.com/guinea_pig/file_upload");
        //WebElement upload = driver.findElement(By.id("myfile"));
        //upload.sendKeys("/Users/mattdunn/temp/big_photo.jpg");
        //upload.sendKeys("/Users/mattdunn/temp/sample1_l.jpg");
        //driver.findElement(By.id("submit")).click();
        //driver.findElement(By.tagName("img"));
        //assertEquals("big_photo.jpg (image/jpeg)", driver.findElement(By.tagName("p")).getText());
        //assertEquals("sample1_l.jpg (image/jpeg)", driver.findElement(By.tagName("p")).getText());

        driver.get("http://the-internet.herokuapp.com/javascript_alerts");
        driver.findElements(By.cssSelector("button")).get(1).click();
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.alertIsPresent());
        Thread.sleep(5000);
        driver.switchTo().alert().accept();



        //driver.quit();
    }

}

