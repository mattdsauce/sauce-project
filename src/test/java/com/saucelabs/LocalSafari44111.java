package com.saucelabs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;



public class LocalSafari44111 {

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
    public void realtorTest() throws InterruptedException {
        driver.get("http://beta.realtor.com/");
        driver.findElement(By.xpath("//*[@id='property-status-wrapper']/div/label[text()='Rent']")).click();
        WebElement searchbox = driver.findElement(By.xpath("//*[@id='searchBox']"));
        searchbox.clear();
        searchbox.sendKeys("Los");
        WebElement predtext = driver.findElement(By.xpath("//div[@class='found-search-results js-found-search-results']"));
        predtext.click();
        /*String page = driver.getPageSource();
        System.out.println(page);
        if(driver.getPageSource().contains("canonical"))
            System.out.println("Text is present in the page");
        else
            System.err.println("Text is not present in the page");
        */
        System.out.println("clicked on predtext");
    }
}